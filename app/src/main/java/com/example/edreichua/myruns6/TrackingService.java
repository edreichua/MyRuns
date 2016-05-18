package com.example.edreichua.myruns6;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * Created by edreichua on 4/28/16.
 */
public class TrackingService extends Service implements LocationListener, SensorEventListener {

    // Variables dealing with location manager
    LocationManager locationManager;
    String provider;

    // Variables dealing with notifications
    NotifyServiceReceiver notifyServiceReceiver;
    final static String ACTION = "NotifyServiceAction";
    final static String STOP_SERVICE_BROADCAST_KEY="StopServiceBroadcastKey";
    final static int RQS_STOP_SERVICE = 1;
    private static boolean isRunning = false;

    // Creates an instance of TrackingBinder class
    private TrackingBinder trackingBinder = new TrackingBinder();

    // Variables dealing with database
    private ExerciseEntry entry;
    private ArrayList<LatLng> locList;
    private Timer durationTimer;
    private int timePassed;
    private Location previousLoc;

    // Variables dealing with activity recognition
    private static ArrayBlockingQueue<Double> sensorQ;
    private OnSensorChangedTask mAsyncTask;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    final static int BLOCK_SIZE = 64;


    @Override
    public void onCreate() {

        // Sets up exercise entry
        initExerciseEntry();

        // Sets up receiver
        notifyServiceReceiver = new NotifyServiceReceiver();

        // Indicates that the Tracking Service is now running
        isRunning = true;

        // Create queue
        sensorQ =new ArrayBlockingQueue<Double>(2048);

        Log.d("Testing", "Tracking service created");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Set up sensor manager
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_FASTEST);

        // Set up location manager
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        // Set up criteria
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        provider = locationManager.getBestProvider(criteria, true);

        // Get most recent location
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                Location l = locationManager.getLastKnownLocation(provider);
                startLocationUpdates(l,true);
            }
        }, 1);

        // Request for locations
        locationManager.requestLocationUpdates(provider, 0, 0, this);
        Log.d("Testing", "Location manager created");

        // Set up notification
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION);
        registerReceiver(notifyServiceReceiver, intentFilter);
        setUpNotification();

        // Set up activity async task
        Log.d("Testing","Create onSensorChangedTask");
        mAsyncTask = new OnSensorChangedTask();

        // Execute the activity async task, accounting for version
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            mAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }else{
            mAsyncTask.execute();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    // Binder class that returns the Tracking Service
    public class TrackingBinder extends Binder {
        public TrackingService getReference() {
            return TrackingService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return trackingBinder;
    }

    @Override
    public void onDestroy() {

        // Cancel the activity async task
        mAsyncTask.cancel(true);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mSensorManager.unregisterListener(this);

        // Unregisters the broadcast receiver
        locationManager.removeUpdates(this);
        this.unregisterReceiver(notifyServiceReceiver);

        // Indicates that the Tracking Service is no longer running
        isRunning = false;

        Log.d("Testing", "Tracking service destroyed");
        super.onDestroy();
    }

    // Sets up the notification to bring user back to app
    public void setUpNotification(){

        Context context = getApplicationContext();
        String notificationTitle = "MyRuns";
        String notificationText = "Recording your path now";

        Intent resultIntent = new Intent(context, MapDisplayActivity.class);

        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resultIntent.setAction(Intent.ACTION_MAIN);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText).setSmallIcon(R.drawable.icon)
                .setContentIntent(pendingIntent).build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification.flags = notification.flags
                | Notification.FLAG_ONGOING_EVENT;

        notificationManager.notify(0, notification);
    }

    // Initializes the exercise entry
    public void initExerciseEntry(){

        // Sets timePassed variable to increment every 1 second
        timePassed = 0;
        durationTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                timePassed++;
            }
        };
        durationTimer.schedule(timerTask, 0, 1000);

        // Sets up Exercise entry
        entry = new ExerciseEntry();
        locList = new ArrayList<LatLng>();
        entry.setmLocationList(locList);
        entry.setmDateTime(Calendar.getInstance().getTimeInMillis());
        entry.setmDistance(0);
        entry.setmDuration(0);
        entry.setmCalorie(0);
        entry.setmHeartRate(0);
    }

    public ExerciseEntry getExerciseEntry(){
        return entry;
    }

    public int getTimePassed(){
        return timePassed;
    }

    public static boolean isRunning() {
        return isRunning;
    }

    public void startLocationUpdates(Location location, boolean isFirst){

        // If there is indeed a location given
        if (location != null) {

            synchronized (entry.getmLocationList()) {
                // Adds the given location to the entry
                LatLng mLatLng = fromLocationToLatLng(location);
                entry.addmLocationList(mLatLng);

                // Prepares intent to notify the MapDisplayActivity of a location update
                Intent intent = new Intent();
                intent.setAction(MapDisplayActivity.ACTION);

                // If this is the first location point
                if (isFirst) {
                    intent.putExtra(MapDisplayActivity.UPDATE_BROADCAST_KEY,
                            MapDisplayActivity.FIRST_LOC);
                } else {
                    onUpdate(location);
                    intent.putExtra(MapDisplayActivity.UPDATE_BROADCAST_KEY,
                            MapDisplayActivity.RQS_UPDATE_LOC);
                }

                // Gets current speed and converts to km/hr
                intent.putExtra(MapDisplayActivity.CURR_SPEED, location.getSpeed() * 3.6);

                // Notify the MapDisplayActivity of a location update
                sendBroadcast(intent);
                Log.d("Testing", "Sent location broadcast");
            }
        }
    }

    public void startActivityUpdate(double classify){

        // Prepares intent to notify the MapDisplayActivity of an activity type update
        Intent intent = new Intent();
        intent.setAction(MapDisplayActivity.ACTION);

        // Adds to the intent the message that there's been an activity type update
        intent.putExtra(MapDisplayActivity.UPDATE_BROADCAST_KEY,
                    MapDisplayActivity.RQS_UPDATE_ACTIVITY);

        // Sets up activity type based on classify parameter
        int activityType;
        switch ((int) classify) {
            case 0:
                // Set to "Standing"
                activityType = 2;
                break;
            case 1:
                // Set to "Walking"
                activityType = 1;
                break;
            case 2:
                // Set to "Running"
                activityType = 0;
                break;
            default:
                // Set to "Other"
                activityType = 13;
                break;
        }

        // Adds the activity type to the message to notify MapDisplayActivity
        intent.putExtra(StartFragment.ACTIVITY_TYPE,
                activityType);

        // Notify the MapDisplayActivity of a activity update
        sendBroadcast(intent);
        Log.d("Testing", "Sent activity type broadcast. Activity type: " + activityType);
    }

    // Updates data for the entry
    public void onUpdate(Location location){

        // Compare location and previousLocation (for distance and climb)
        if (location != null) {

            double additionalDistance = 0;
            double additionalClimb = 0;

            if (previousLoc != null) {

                // Update the additional distance traveled
                additionalDistance = (double) location.distanceTo(previousLoc) / 1000;

                // Update the additional climb
                additionalClimb = (location.getAltitude() - previousLoc.getAltitude()) / 1000;
            }

            // Reset the previous location to be the current location
            previousLoc = location;

            // Set parameters
            entry.setmDistance(entry.getmDistance() + additionalDistance);
            entry.setmClimb(entry.getmClimb() + additionalClimb);

            Log.d("Testing", "additionalDistance = " + additionalDistance);
            Log.d("Testing", "additionalClimb = " + additionalClimb);
            Log.d("Testing", "entry total distance = " + entry.getmDistance());
            Log.d("Testing", "entry total climb = " + entry.getmClimb());
        }

        double distance = entry.getmDistance();

        if (distance > 0) {

            // Update the average speed
            entry.setmAvgSpeed(60 * 60 * distance / timePassed);

            // Update the calories
            entry.setmCalorie( (int) ((distance/ManualEntryActivity.MILES2KM) * ManualEntryActivity.MILES2CAL) );

            Log.d("Testing", "entry avg speed (km/hr) = " + entry.getmAvgSpeed());
            Log.d("Testing", "entry total calories = " + entry.getmCalorie());
        }
    }

    public void notifyChange(){

    }

    public class NotifyServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int req = intent.getIntExtra(STOP_SERVICE_BROADCAST_KEY, 0);
            if (req == RQS_STOP_SERVICE){
                Log.d("Testing", "Service stopped");
                ((NotificationManager) getSystemService(NOTIFICATION_SERVICE))
                        .cancelAll();
            }
        }
    }


    /////////////////////// Updating location functionality ///////////////////////

    // Converts location to LatLng
    public static LatLng fromLocationToLatLng(Location location){
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    public void onLocationChanged(Location location) {
        Log.d("Testing", "Location change");
        startLocationUpdates(location, false);
    }

    public void onProviderDisabled(String provider) {}
    public void onProviderEnabled(String provider) {}
    public void onStatusChanged(String provider, int status, Bundle extras) {}


    /////////////////////// Sensor functionality ///////////////////////

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onSensorChanged(SensorEvent event) {

        // Discard anything but linear acceleration
        if (event.sensor.getType() != Sensor.TYPE_LINEAR_ACCELERATION)
            return;

        // Determine the euclidean acceleration
        double euclideanAcc = Math.sqrt(event.values[0] * event.values[0]
                + event.values[1] * event.values[1] + event.values[2]
                * event.values[2]);

        // Check if sensorQ has reached capacity
        try {
            // If can fit it, add the acceleration to the queue
            sensorQ.add(new Double(euclideanAcc));
        }
        // Otherwise, create a new, larger buffer to use for the queue
        catch (IllegalStateException e) {

            // This doubles the buffer
            ArrayBlockingQueue<Double> newBuf = new ArrayBlockingQueue<Double>(
                    sensorQ.size() * 2);

            sensorQ.drainTo(newBuf);
            sensorQ = newBuf;
            sensorQ.add(new Double(euclideanAcc));
        }
    }

    private class OnSensorChangedTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {

            // Feature vector
            Double[] featVec = new Double[BLOCK_SIZE + 1];

            int blockSize = 0;
            FFT fft = new FFT(BLOCK_SIZE);

            // Acceleration block to use
            double[] accBlock = new double[BLOCK_SIZE];
            double[] re = accBlock;
            double[] im = new double[BLOCK_SIZE];
            Log.d("Testing", "Do in background");

            // Continuously runs in background
            while (true) {
                try {

                    // Checks whether AsyncTask is cancelled in the while loop
                    if (isCancelled () == true){
                        return null;
                    }

                    // Adds to the block from the sensor queue
                    try {
                        accBlock[blockSize++] = sensorQ.take().doubleValue();
                    }catch(Exception e) {
                        Log.d("Testing", "Discard buffer");
                    }

                    // Reset the block when it has 64 items
                    if (blockSize >= BLOCK_SIZE) {
                        blockSize = 0;

                        // Finds the max value from the block
                        double max = .0;
                        for (double val : accBlock) {
                            if (max < val) {
                                max = val;
                            }
                        }
                        featVec[BLOCK_SIZE] = max;

                        fft.fft(re, im);

                        // Gets the magnitude for the feature vector
                        for (int i = 0; i < re.length; i++) {
                            double mag = Math.sqrt(re[i] * re[i] + im[i]
                                    * im[i]);
                            featVec[i] = mag;
                            im[i] = .0;
                        }

                        // Classifies the feature vector using Weka
                        double classify = WekaClassifier.classify(featVec);

                        // Passes the classified activity type to the activity update method
                        startActivityUpdate(classify);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}