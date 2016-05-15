package com.example.edreichua.myapplication.backend.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by edreichua on 5/14/16.
 */
public class ExerciseDataStore {

    private static final Logger mLogger = Logger
            .getLogger(ExerciseDataStore.class.getName());
    private static final DatastoreService mDatastore = DatastoreServiceFactory
            .getDatastoreService();

    private static Key getKey() {
        return KeyFactory.createKey(ExerciseData.EXERCISE_PARENT_ENTITY_ID,
                ExerciseData.EXERCISE_PARENT_KEY_ID);
    }


    public static boolean add(ExerciseData entry) {

        Key parentKey = getKey();

        Entity entity = new Entity(ExerciseData.EXERCISE_ENTITY_ID, entry.mID,
                parentKey);

        entity.setProperty(ExerciseData.FIELD_NAME_ID, entry.mID);
        entity.setProperty(ExerciseData.FIELD_NAME_INPUT, entry.mInput);
        entity.setProperty(ExerciseData.FIELD_NAME_INPUT, entry.mInput);
        entity.setProperty(ExerciseData.FIELD_NAME_ACTIVITY, entry.mActivity);
        entity.setProperty(ExerciseData.FIELD_NAME_DATETIME, entry.mDateTime);
        entity.setProperty(ExerciseData.FIELD_NAME_DURATION, entry.mDuration);
        entity.setProperty(ExerciseData.FIELD_NAME_DISTANCE, entry.mDistance);
        entity.setProperty(ExerciseData.FIELD_NAME_AVGSPEED, entry.mAverageSpeed);
        entity.setProperty(ExerciseData.FIELD_NAME_CALORIES, entry.mCalories);
        entity.setProperty(ExerciseData.FIELD_NAME_CLIMB, entry.mClimb);
        entity.setProperty(ExerciseData.FIELD_NAME_HEARTRATE, entry.mHeartRate);
        entity.setProperty(ExerciseData.FIELD_NAME_COMMENT, entry.mComment);

        mDatastore.put(entity);

        return true;
    }


    public static boolean delete(int id) {
        // you can also use name to get key, then use the key to delete the
        // entity from datastore directly
        // because name is also the entity's key

        // query
        Query.Filter filter = new Query.FilterPredicate(ExerciseData.FIELD_NAME_ID,
                Query.FilterOperator.EQUAL, id);

        Query query = new Query(ExerciseData.EXERCISE_ENTITY_ID);
        query.setFilter(filter);

        // Use PreparedQuery interface to retrieve results
        PreparedQuery pq = mDatastore.prepare(query);

        Entity result = pq.asSingleEntity();
        boolean ret = false;
        if (result != null) {
            // delete
            mDatastore.delete(result.getKey());
            ret = true;
        }

        return ret;
    }

    public static ArrayList<ExerciseData> query() {
        ArrayList<ExerciseData> resultList = new ArrayList<>();

        Query query = new Query(ExerciseData.EXERCISE_ENTITY_ID);
        // get every record from datastore, no filter
        query.setFilter(null);
        // set query's ancestor to get strong consistency
        query.setAncestor(getKey());

        PreparedQuery pq = mDatastore.prepare(query);

        for (Entity entity : pq.asIterable()) {
            ExerciseData data = getExerciseFromEntity(entity);
            if (data != null) {
                resultList.add(data);
            }
        }

        return resultList;
    }

    private static ExerciseData getExerciseFromEntity(Entity entity) {
        if (entity == null) {
            return null;
        }

        return new ExerciseData(
            (String) entity.getProperty(ExerciseData.FIELD_NAME_ID),
            (String) entity.getProperty(ExerciseData.FIELD_NAME_INPUT),
            (String) entity.getProperty(ExerciseData.FIELD_NAME_ACTIVITY),
            (String) entity.getProperty(ExerciseData.FIELD_NAME_DATETIME),
            (String) entity.getProperty(ExerciseData.FIELD_NAME_DURATION),
            (String) entity.getProperty(ExerciseData.FIELD_NAME_DISTANCE),
            (String) entity.getProperty(ExerciseData.FIELD_NAME_AVGSPEED),
            (String) entity.getProperty(ExerciseData.FIELD_NAME_CALORIES),
            (String) entity.getProperty(ExerciseData.FIELD_NAME_CLIMB),
            (String) entity.getProperty(ExerciseData.FIELD_NAME_HEARTRATE),
            (String) entity.getProperty(ExerciseData.FIELD_NAME_COMMENT));
    }
}
