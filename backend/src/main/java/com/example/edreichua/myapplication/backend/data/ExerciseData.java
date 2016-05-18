package com.example.edreichua.myapplication.backend.data;

/**
 * Created by edreichua on 5/14/16.
 */
public class ExerciseData {

    // ID for entity
    public static final String EXERCISE_PARENT_ENTITY_ID = "ExerciseParent";
    public static final String EXERCISE_PARENT_KEY_ID = "ExerciseParent";
    public static final String EXERCISE_ENTITY_ID = "ID";
    public static final String FIELD_NAME_ID = "id";

    // String constants for input in entity
    public static final String FIELD_NAME_INPUT = "input";
    public static final String FIELD_NAME_ACTIVITY = "activity";
    public static final String FIELD_NAME_DATETIME = "datetime";
    public static final String FIELD_NAME_DURATION = "duration";
    public static final String FIELD_NAME_DISTANCE = "distance";
    public static final String FIELD_NAME_AVGSPEED = "avgspeed";
    public static final String FIELD_NAME_CALORIES = "calories";
    public static final String FIELD_NAME_CLIMB = "climb";
    public static final String FIELD_NAME_HEARTRATE = "heartrate";
    public static final String FIELD_NAME_COMMENT = "comment";

    // Components of entity
    public String mID, mInput, mActivity, mDateTime, mDuration, mDistance, mAverageSpeed, mCalories,
            mClimb, mHeartRate, mComment;

    // Constructor for entity
    public ExerciseData(String _id, String _input, String _activity, String _dateTime,
                        String _duration, String _distance, String _avgSpeed,
                        String _calories, String _climb, String _heartrate, String _comment) {

        // Initialize parameters
        mID = _id;
        mInput = _input;
        mActivity = _activity;
        mDateTime = _dateTime;
        mDuration = _duration;
        mDistance = _distance;
        mAverageSpeed = _avgSpeed;
        mCalories = _calories;
        mClimb = _climb;
        mHeartRate = _heartrate;
        mComment = _comment;
    }
}