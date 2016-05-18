package com.example.edreichua.myruns6;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;


public class GcmIntentService extends IntentService {

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    /**
     * Handle intent from wakeful broadcast receiver
     */
    protected void onHandleIntent(Intent intent) {

        // Get the extras from the intent
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        // Retrieve the message type of the intent
        String messageType = gcm.getMessageType(intent);
        Log.d("Testing", "Intent triggered");

        // Make sure that we are receiving a message
        if (extras != null && !extras.isEmpty()) {

            // Make sure the message type is "gcm"
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                // Get the row ID to perform deleting
                long rowid = Long.parseLong(extras.getString("message"));

                // Delete entry from the database
                ExerciseEntryDbHelper dbHelper = new ExerciseEntryDbHelper(this);
                dbHelper.removeEntry(rowid);
            }
        }

        // Finish the service intent, while keeping device awake
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}