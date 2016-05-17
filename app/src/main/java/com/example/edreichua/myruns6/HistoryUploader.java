package com.example.edreichua.myruns6;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by edreichua on 5/17/16.
 */

/**
 * History uploader to retrieve history entries and post it to server utilities
 */
public class HistoryUploader extends AsyncTask<Void, Void, String>{

    @Override
    protected String doInBackground(Void... arg0) {

        try{
            // Fetch all the entries from history
            ArrayList<ExerciseEntry> entries = MainActivity.DBhelper.fetchEntries();

            if(entries == null)
                return null;

            // Create a json array
            JSONArray jOuterArray = new JSONArray();

            // Input entries to json array
            for(ExerciseEntry entry: entries){

                // Create a json object for each entry
                JSONObject jInnerObject = new JSONObject();
                Log.d("Testing Acivity", Integer.toString(entry.getmActivityType()));

                // Insert components of entries into json object
                jInnerObject.put(Globals.FIELD_NAME_ID,
                        Long.toString(entry.getmId()));
                jInnerObject.put(Globals.FIELD_NAME_INPUT,
                        StartFragment.ID_TO_INPUT[entry.getmInputType()]);
                jInnerObject.put(Globals.FIELD_NAME_ACTIVITY,
                        StartFragment.ID_TO_ACTIVITY[entry.getmActivityType()]);
                jInnerObject.put(Globals.FIELD_NAME_DATETIME,
                        HistoryFragment.formatDateTime(entry.getmDateTime()));
                jInnerObject.put(Globals.FIELD_NAME_DURATION,
                        HistoryFragment.formatDuration(entry.getmDuration()));
                jInnerObject.put(Globals.FIELD_NAME_DISTANCE,
                        HistoryFragment.formatDistance(entry.getmDistance(), "Miles"));
                jInnerObject.put(Globals.FIELD_NAME_AVGSPEED,
                        MapDisplayActivity.formatAvgSpeed(entry.getmAvgSpeed(), "Miles"));
                jInnerObject.put(Globals.FIELD_NAME_CALORIES,
                        MapDisplayActivity.formatCalories(entry.getmCalorie()));
                jInnerObject.put(Globals.FIELD_NAME_CLIMB,
                        MapDisplayActivity.formatClimb(entry.getmClimb(), "Miles"));
                jInnerObject.put(Globals.FIELD_NAME_HEARTRATE,
                        Integer.toString(entry.getmHeartRate()));
                jInnerObject.put(Globals.FIELD_NAME_COMMENT,
                        entry.getmComment()+" ");
                Log.d("Testing inner", jInnerObject.toString());

                // Add json object to json array
                jOuterArray.put(jInnerObject);
            }
            Log.d("Testing outer", jOuterArray.toString());

            // Save parameters to map
            Map<String,String> params = new HashMap<>();
            params.put("result",jOuterArray.toString());
            params.put("regId", Globals.regID);

            // Call server utilities
            ServerUtilities.post(Globals.URL+"/PostData.do", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
