package com.example.edreichua.myapplication.backend;

import com.example.edreichua.myapplication.backend.data.ExerciseData;
import com.example.edreichua.myapplication.backend.data.ExerciseDataStore;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by edreichua on 5/14/16.
 */
public class PostDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {


        // Get parameters from req
        String jArrayString = req.getParameter("result");
        String regID = req.getParameter("name");


       //  Get Json Array
        JSONArray jArray = null;
        try {
            jArray = new JSONArray(jArrayString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        if(regID!=null) {
//            resp.setContentType("text/html");
//            PrintWriter out = resp.getWriter();
//            out.println(regID);
//            out.close();
//        }else{
//            resp.setContentType("text/html");
//            PrintWriter out = resp.getWriter();
//            out.println("This is null");
//            out.close();
//        }

        ArrayList<ExerciseData> result = new ArrayList<>();

        for(int i = 0; i < jArray.length(); i++) {

            try {
                JSONObject jsonOuterObject = jArray.getJSONObject(i);

                // Get parameters from client
                String id = (String) jsonOuterObject.get(ExerciseData.FIELD_NAME_ID);
                String input = (String) jsonOuterObject.get(ExerciseData.FIELD_NAME_INPUT);
                String activity = (String) jsonOuterObject.get(ExerciseData.FIELD_NAME_ACTIVITY);
                String datetime = (String) jsonOuterObject.get(ExerciseData.FIELD_NAME_DATETIME);
                String duration = (String) jsonOuterObject.get(ExerciseData.FIELD_NAME_DURATION);
                String distance = (String) jsonOuterObject.get(ExerciseData.FIELD_NAME_DISTANCE);
                String avgspeed = (String) jsonOuterObject.get(ExerciseData.FIELD_NAME_AVGSPEED);
                String calories = (String) jsonOuterObject.get(ExerciseData.FIELD_NAME_CALORIES);
                String climb = (String) jsonOuterObject.get(ExerciseData.FIELD_NAME_CLIMB);
                String heartrate = (String) jsonOuterObject.get(ExerciseData.FIELD_NAME_HEARTRATE);
                String comment = (String) jsonOuterObject.get(ExerciseData.FIELD_NAME_COMMENT);

                // Set ExerciseData for server
                ExerciseData entry = new ExerciseData(id, input, activity, datetime, duration,
                        distance, avgspeed, calories, climb, heartrate, comment);
                boolean ret = ExerciseDataStore.add(entry);

                if (ret) {
                    result.add(entry);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        req.setAttribute("result", result);
        getServletContext().getRequestDispatcher("/query.jsp").forward(
                req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doGet(req, resp);
    }

}
