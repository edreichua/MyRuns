package com.example.edreichua.myruns6;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.logging.Level;
import java.util.logging.Logger;


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
        Log.d("Testing intent", "triggered");

        // Make sure that we are receiving a message
        if (extras != null && !extras.isEmpty()) {

            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                // Get the rowid to perform deleting
                long rowid = Long.parseLong(extras.getString("message"));
                MainActivity.DBhelper.removeEntry(rowid);
            }
        }

        // Finish the service intent, while keeping device awake
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

}