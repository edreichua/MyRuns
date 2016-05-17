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

    // Obtain data store service as a static constant
    private static final DatastoreService mDatastore = DatastoreServiceFactory
            .getDatastoreService();

    // Retrieve key
    private static Key getKey() {
        return KeyFactory.createKey(ExerciseData.EXERCISE_PARENT_ENTITY_ID,
                ExerciseData.EXERCISE_PARENT_KEY_ID);
    }

    /**
     * Add entity to exercise data store
     * @param entry
     * @return
     */
    public static boolean add(ExerciseData entry) {

        // Get the key
        Key parentKey = getKey();

        // Create an entity
        Entity entity = new Entity(ExerciseData.EXERCISE_ENTITY_ID, entry.mID,
                parentKey);

        // Set property of the entity
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

        // Add entity to datastore
        mDatastore.put(entity);

        return true;
    }


    /**
     * Delete entity from data store
     * @param id
     * @return true if deleted successfully, and false otherwise
     */
    public static boolean delete(String id) {

        // Query the entity with the same row id
        Query.Filter filter = new Query.FilterPredicate(ExerciseData.FIELD_NAME_ID,
                Query.FilterOperator.EQUAL, id);
        Query query = new Query(ExerciseData.EXERCISE_ENTITY_ID);
        query.setFilter(filter);

        // Use PreparedQuery interface to retrieve results
        PreparedQuery pq = mDatastore.prepare(query);
        Entity result = pq.asSingleEntity();

        // Delete from data store
        boolean ret = false;
        if (result != null) {
            mDatastore.delete(result.getKey());
            ret = true;
        }

        // Return true if deleted successfully, and false otherwise
        return ret;
    }

    /**
     * query method to return an arraylist of all the entities in the data store
     * @return
     */
    public static ArrayList<ExerciseData> query() {

        // Initialise the arraylist to be returned
        ArrayList<ExerciseData> resultList = new ArrayList<>();

        // Get every entity from the datastore
        Query query = new Query(ExerciseData.EXERCISE_ENTITY_ID);
        query.setFilter(null);
        query.setAncestor(getKey());
        PreparedQuery pq = mDatastore.prepare(query);

        // Use an iterable to retrieve all the entities from datastore
        for (Entity entity : pq.asIterable()) {
            ExerciseData data = getExerciseFromEntity(entity);
            if (data != null) {
                resultList.add(data);
            }
        }

        // return array list
        return resultList;
    }

    /**
     * Retrieve exercise data from entity
     * @param entity
     * @return
     */
    private static ExerciseData getExerciseFromEntity(Entity entity) {

        // Error check
        if (entity == null) {
            return null;
        }

        // Create new exercise data by calling the constructor
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
