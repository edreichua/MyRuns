package com.example.edreichua.myruns6;

import android.content.AsyncTaskLoader;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by edreichua on 4/22/16.
 */

public class MapDisplayActivity extends FragmentActivity implements ServiceConnection {

    // Variables dealing with service connection
    private ServiceConnection mConnection = this;
    private TrackingService trackingService;
    private Intent serviceIntent;

    // Variables dealing with broadcast
    public final static String ACTION = "NotifyLocationUpdate";
    public final static String UPDATE_BROADCAST_KEY = "UpdateBroadcastKey";
    public final static int RQS_UPDATE_LOC = 1;
    public final static int RQS_UPDATE_ACTIVITY = 3;
    public final static int FIRST_LOC = 2;
    private UpdateReceiver updateReceiver;
    private boolean mIsBound;

    // Variables dealing with history
    public final static String NOT_DRAWN = "NotDrawn";
    private boolean isHistory, notDrawn, updateMap, isAutomatic;
    private int inputType;
    public static long rowId;

    // Variables dealing with the map
    private GoogleMap mMap;
    private Marker startLoc, endLoc;
    private double currSpeed;
    private int currActivity;
    public final static String CURR_SPEED = "CurrentSpeed";

    // Variables dealing with activity types
    private int runCount, walkCount, standCount;
    private final static String RUN_COUNT = "RunCount";
    private final static String WALK_COUNT = "WalkCount";
    private final static String STAND_COUNT = "StandCount";

    // Variables dealing with database
    private ExerciseEntryDbHelper mEntryDbHelper;
    private ExerciseEntry entry;


    /////////////////////// Override core functionality ///////////////////////

    /**
     * Handle creating of activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Create main layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_display);

        // Set up the map
        setUpMapIfNeeded();

        // Get Bundle
        Bundle bundle;
        if (savedInstanceState == null ) {
            bundle = getIntent().getExtras();
        } else {
            bundle = savedInstanceState;
            updateMap = true;
        }

        if(bundle != null) {
            // Used to indicate whether map is displayed in history fragment
            isHistory = bundle.getBoolean(HistoryFragment.FROM_HISTORY, false);

            // Used to access data row
            rowId = bundle.getLong(HistoryFragment.ROW_INDEX, 0);

            // Used for zoom animation
            notDrawn = bundle.getBoolean(NOT_DRAWN, true);

            // Check if is Automatic
            inputType = bundle.getInt(StartFragment.INPUT_TYPE,-1);
            isAutomatic = inputType
                    == StartFragment.INPUT_TO_ID_MAP.get(StartFragment.AUTOMATIC) ? true : false;

            // Used for activity type counts
            runCount = bundle.getInt(RUN_COUNT, 0);
            walkCount = bundle.getInt(WALK_COUNT, 0);
            standCount = bundle.getInt(STAND_COUNT, 0);
        }

        mEntryDbHelper = new ExerciseEntryDbHelper(this);

        // If the map is recording a new exercise
        if (!isHistory) {
            // Start service
            startService();

            // Start broadcast receiver
            updateReceiver = new UpdateReceiver();

            // Bind service
            mIsBound = false;
            bindService();

        } else {
            // Remove buttons
            (findViewById(R.id.button_save_gps)).setVisibility(View.GONE);
            (findViewById(R.id.button_cancel_gps)).setVisibility(View.GONE);

            // Retrieve entry
            entry = new ReadFromDB(this).loadInBackground();

            // Draw trace and update stats
            drawHistoryOnMap();
            updateStat();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(HistoryFragment.FROM_HISTORY, isHistory);
        outState.putLong(HistoryFragment.ROW_INDEX, rowId);
        outState.putBoolean(NOT_DRAWN, notDrawn);
        outState.putInt(StartFragment.INPUT_TYPE, inputType);

        // Save the counts of each activity type
        outState.putInt(RUN_COUNT, runCount);
        outState.putInt(WALK_COUNT, walkCount);
        outState.putInt(STAND_COUNT, standCount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();

        if (!isHistory) {
            bindService();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ACTION);
            registerReceiver(updateReceiver, intentFilter);

        }
    }

    protected void onPause() {
        // If the map is recording a new exercise
        if (!isHistory) {
            unregisterReceiver(updateReceiver);
            unbindService();
        }
        super.onPause();
    }


    /////////////////////// Binding with Tracking Service ///////////////////////

    // Starts the Tracking Service
    public void startService() {
        serviceIntent = new Intent(this, TrackingService.class);

        if (getParentActivityIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            serviceIntent.putExtras(bundle);
        }

        startService(serviceIntent);
    }

    // Binds the Tracking Service
    public void bindService() {
        // If it is not already bound
        if (!mIsBound) {
            // Bind it
            bindService(this.serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
            // Indicate that it is now bound
            mIsBound = true;
        }
    }

    // Unbinds the Tracking Service
    public void unbindService() {
        // If it is currently bound
        if (mIsBound) {
            // Unbind it
            unbindService(this.mConnection);
            // Indicate that it is now unbound
            mIsBound = false;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        // Set trackingService to be the Tracking Service
        trackingService = ((TrackingService.TrackingBinder) service).getReference();

        if (updateMap) {
            getExerciseEntryFromService();
        }

        Log.d("Testing", "Service connected");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        // Disconnect the Tracking Service
        trackingService = null;
        Log.d("Testing", "Service disconnected");
    }

    @Override
    protected void onDestroy() {
        // If being destroyed because it's being closed
        if (isFinishing())
            exitHelper();
        super.onDestroy();
    }


    /////////////////////// Broadcast receiver ///////////////////////

    public class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (trackingService != null) {

                Log.d("Testing", "received");

                // Indicates whether this is location update (and whether it is the first
                // location update or not) or activity update
                int broadCastType = intent.getIntExtra(UPDATE_BROADCAST_KEY, 0);

                // Sets entry
                getExerciseEntryFromService();

                // Sets current speed
                currSpeed = intent.getDoubleExtra(CURR_SPEED, 0);

                // Check if it is an activity type update
                if (broadCastType == RQS_UPDATE_ACTIVITY) {
                    currActivity = intent.getIntExtra(StartFragment.ACTIVITY_TYPE, 0);
                    switch (currActivity) {
                        case 0: // if "Running"
                            runCount++;
                            break;
                        case 1: // if "Walking"
                            walkCount++;
                            break;
                        case 2: // if "Standing"
                            standCount++;
                            break;
                        default:
                            break;
                    }
                }

                // Draw trace and update stats
                drawTraceOnMap(broadCastType);
                updateStat();
            }
        }
    }


    /////////////////////// Handle Selection of buttons ///////////////////////

    /**
     * Handle the selection of the save button
     *
     * @param v
     */
    public void selectGPSSave(View v) {
        saveEntryToDb();
        exitHelper();
        finish();
    }

    /**
     * Handle the selection of the cancel button
     *
     * @param v
     */
    public void selectGPSCancel(View v) {
        // Inform user that the profile information is discarded
        Toast.makeText(getApplicationContext(), getString(R.string.ui_toast_cancel),
                Toast.LENGTH_SHORT).show();

        // Close the activity
        exitHelper();
        finish();
    }

    @Override
    public void onBackPressed() {
        exitHelper();
        finish();
        super.onBackPressed();
    }

    private void exitHelper() {
        if (!isHistory) {
            if (trackingService != null) {

                // Destroy notification and tracking service
                Intent intent = new Intent();
                intent.setAction(TrackingService.ACTION);
                intent.putExtra(TrackingService.STOP_SERVICE_BROADCAST_KEY, TrackingService.RQS_STOP_SERVICE);
                sendBroadcast(intent);

                unbindService();
                stopService(serviceIntent);

                Log.d("Testing", "Service destroyed");
            }
        }
    }

    // To delete data entry
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (isHistory) {
            menu.add(Menu.NONE, 0, 0, "DELETE").
                    setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    // Perform the removal on a thread
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        runThread();
        finish();
        return true;
    }

    private void runThread() {
        new Thread() {
            public void run() {
                try {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            MainActivity.DBhelper.removeEntry(rowId);
                            HistoryFragment.adapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    /////////////////////// Updating location functionality ///////////////////////

    // Draws an entry selected from history
    public void drawHistoryOnMap() {
        // Get location list
        ArrayList<LatLng> latLngList = entry.getmLocationList();

        // Get start location
        LatLng latlng = latLngList.get(0);
        startLoc = mMap.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_GREEN)));

        // Animate if not drawn
        if (notDrawn) {
            // Zoom in
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,
                    17));
            notDrawn = false;
        }

        // Draw polyline
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.BLACK);
        polylineOptions.width(7);
        polylineOptions.addAll(latLngList);
        mMap.addPolyline(polylineOptions);

        // Draw the end marker
        endLoc = mMap.addMarker(new MarkerOptions().position(latLngList.get(latLngList.size() - 1))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

    }

    // Draws progress while recording a new exercise
    public void drawTraceOnMap(int isFirst) {

        synchronized (entry.getmLocationList()) {

            // If this is the first point
            if (isFirst == FIRST_LOC && entry.getmLocationList() != null && entry.getmLocationList().size() == 1) {

                // Set input type and activity type
                Bundle bundle = getIntent().getExtras();
                int input = bundle.getInt(StartFragment.INPUT_TYPE, 0);
                entry.setmInputType(input);

                // Check if from GPS or Automatic
                if(!isAutomatic) {
                    entry.setmActivityType(bundle.getInt(StartFragment.ACTIVITY_TYPE, 0));
                }
                else{
                    int maxCount = Math.max(Math.max(runCount, walkCount), standCount);

                    // Set the current activity based on the majority activity
                    if (maxCount == runCount)
                        currActivity = 0; // set to "Running"
                    else if (maxCount == walkCount)
                        currActivity = 1; // set to "Walking"
                    else
                        currActivity = 2; // set to "Standing"
                    entry.setmActivityType(currActivity);
                }

                // Draw the start marker
                ArrayList<LatLng> latLngList = entry.getmLocationList();
                startLoc = mMap.addMarker(new MarkerOptions().position(latLngList.get(0)).icon(BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN)));

                // Draw the end marker
                endLoc = mMap.addMarker(new MarkerOptions().position(latLngList.get(latLngList.size() - 1))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                // Zoom in
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngList.get(0),
                        17));

            } else if (entry.getmLocationList() != null && entry.getmLocationList().size() > 1) {

                // Get the start marker
                ArrayList<LatLng> latLngList = entry.getmLocationList();
                if (startLoc != null) {
                    startLoc.remove();
                }
                startLoc = mMap.addMarker(new MarkerOptions().position(latLngList.get(1)).icon(BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN)));

                // Draw polyline
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(Color.BLACK);
                polylineOptions.width(7);

                // Solves an edge case
                ArrayList<LatLng> dummy = new ArrayList<>(latLngList);
                dummy.remove(0);
                polylineOptions.addAll(dummy);
                mMap.addPolyline(polylineOptions);

                // Draw the end marker
                if (endLoc != null) {
                    endLoc.remove();
                }
                endLoc = mMap.addMarker(new MarkerOptions().position(latLngList.get(latLngList.size() - 1))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        }
    }


    /////////////////////// Updating stats functionality ///////////////////////

    // Sets entry to be reference to the desired exercise entry
    public void getExerciseEntryFromService() {
        entry = trackingService.getExerciseEntry();
    }

    public void saveEntryToDb() {
        Log.d("Testing", "saving entry...");

        synchronized (entry.getmLocationList()) {
            // Remove the first point
            if (entry.getmLocationList() != null && entry.getmLocationList().size() > 1)
                entry.getmLocationList().remove(0);

            entry.setmDuration(trackingService.getTimePassed());

            // Execute writing to database
            new WriteToDB().execute();
        }

    }

    /**
     * Update stats on text views
     */
    public void updateStat() {

        // Get unit pref
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String unitPref = pref.getString(getString(R.string.unit_preference), getString(R.string.unit_km));

        // Set the text view for activity type
        TextView gpsType = (TextView) findViewById(R.id.gps_type);
        if(isAutomatic) {
            Log.d("Testing", "run, walk, stand: " + runCount + ", " + walkCount + ", " + standCount);

            // Determine which activity count is the highest (the majority activity)
            int maxCount = Math.max(Math.max(runCount, walkCount), standCount);

            // Set the current activity based on the majority activity
            if (maxCount == runCount)
                currActivity = 0; // set to "Running"
            else if (maxCount == walkCount)
                currActivity = 1; // set to "Walking"
            else
                currActivity = 2; // set to "Standing"

            Log.d("Testing", "currActivity: " + currActivity);
            entry.setmActivityType(currActivity);
        }
        gpsType.setText(formatType(StartFragment.ID_TO_ACTIVITY[entry.getmActivityType()]));

        // Set the text view for average speed
        TextView gpsAvgSpeed = (TextView) findViewById(R.id.gps_avg_speed);
        gpsAvgSpeed.setText("Avg speed: " + formatAvgSpeed(entry.getmAvgSpeed(), unitPref));

        // Set the text view for current speed
        TextView gpsCurSpeed = (TextView) findViewById(R.id.gps_cur_speed);
        gpsCurSpeed.setText(formatCurSpeed(currSpeed, unitPref));

        // Set the text view for climb
        TextView gpsClimb = (TextView) findViewById(R.id.gps_climb);
        gpsClimb.setText("Climb: " + formatClimb(entry.getmClimb(), unitPref));

        // Set the text view for calorie
        TextView gpsCalorie = (TextView) findViewById(R.id.gps_calories);
        gpsCalorie.setText("Calories: "+formatCalories(entry.getmCalorie())+"cal");

        // Set the text view for distance
        TextView gpsDistance = (TextView) findViewById(R.id.gps_distance);
        gpsDistance.setText("Distance: " + formatDistance(entry.getmDistance(), unitPref));
    }


    /////////////////////// Formatting data to display ///////////////////////

    /**
     * format activity type
     *
     * @param activity
     * @return
     */
    public static String formatType(String activity) {
        return "Type: " + activity;
    }

    /**
     * format average speed
     *
     * @param speed
     * @param unitPref
     * @return
     */
    public static String formatAvgSpeed(Double speed, String unitPref) {
        String unit = "km/h";
        if (unitPref.equals("Miles")) {
            speed /= ManualEntryActivity.MILES2KM; // converts from km to miles
            unit = "m/h";
        }
        return String.format("%.2f", speed) + " " + unit;
    }

    /**
     * format current speed
     *
     * @param speed
     * @param unitPref
     * @return
     */
    public String formatCurSpeed(Double speed, String unitPref) {
        if (isHistory)
            return "Cur speed: n/a";

        String unit = "km/h";
        if (unitPref.equals("Miles")) {
            speed /= ManualEntryActivity.MILES2KM; // converts from km to miles
            unit = "m/h";
        }
        return "Cur speed: " + String.format("%.2f", speed) + " " + unit;
    }

    /**
     * format climb
     *
     * @param climb
     * @param unitPref
     * @return
     */
    public static String formatClimb(Double climb, String unitPref) {
        return String.format("%.2f", climb) + " " + unitPref;
    }

    /**
     * format calories
     *
     * @param cal
     * @return
     */
    public static String formatCalories(int cal) {
        return cal+" ";
    }

    /**
     * format distance
     *
     * @param distance
     * @param unitPref
     * @return
     */
    public static String formatDistance(double distance, String unitPref) {
        if (unitPref.equals("Miles")) {
            distance /= ManualEntryActivity.MILES2KM; // converts from km to miles
        }
        return String.format("%.2f", distance) + " " + unitPref;
    }


    /////////////////////// Map functionality ///////////////////////

    /**
     * Function to set up map at the beginning
     */
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * Setting up map, with a point new Africa for visual effect
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Africa"));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }


    /////////////////////// Use AsyncTask to write to database ///////////////////////


    private class WriteToDB extends AsyncTask<ExerciseEntry, Integer, String> {

        @Override
        protected String doInBackground(ExerciseEntry... exerciseEntries) {
            // Insert entry to database
            long id = mEntryDbHelper.insertEntry(entry);
            return ""+id;
        }

        @Override
        protected void onPostExecute(String result) {

            HistoryFragment.adapter.notifyDataSetChanged();

            // Inform user that the entry has been saved
            Toast.makeText(getApplicationContext(), "Entry #"+result+" saved.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    /////////////////////// Use AsyncTaskLoader to read from database ///////////////////////

    public static class ReadFromDB extends AsyncTaskLoader<ExerciseEntry> {

        public ReadFromDB(Context context){
            super(context);
        }

        @Override
        public ExerciseEntry loadInBackground() {
            ExerciseEntry entry = MainActivity.DBhelper.fetchEntryByIndex(rowId);
            return entry;
        }
    }
}
