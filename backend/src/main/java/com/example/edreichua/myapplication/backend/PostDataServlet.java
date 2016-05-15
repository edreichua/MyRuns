package com.example.edreichua.myapplication.backend;

import com.example.edreichua.myapplication.backend.data.ExerciseData;
import com.example.edreichua.myapplication.backend.data.ExerciseDataStore;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlElementDecl;

/**
 * Created by edreichua on 5/14/16.
 */
public class PostDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        // Get parameters from client
        String id = req.getParameter(ExerciseData.FIELD_NAME_ID);
        String input = req.getParameter(ExerciseData.FIELD_NAME_INPUT);
        String activity = req.getParameter(ExerciseData.FIELD_NAME_ACTIVITY);
        String datetime = req.getParameter(ExerciseData.FIELD_NAME_DATETIME);
        String duration = req.getParameter(ExerciseData.FIELD_NAME_DURATION);
        String distance = req.getParameter(ExerciseData.FIELD_NAME_DISTANCE);
        String avgspeed = req.getParameter(ExerciseData.FIELD_NAME_AVGSPEED);
        String calories = req.getParameter(ExerciseData.FIELD_NAME_CALORIES);
        String climb = req.getParameter(ExerciseData.FIELD_NAME_CLIMB);
        String heartrate = req.getParameter(ExerciseData.FIELD_NAME_HEARTRATE);
        String comment = req.getParameter(ExerciseData.FIELD_NAME_COMMENT);


        // Set ExerciseData for server
        ExerciseData entry = new ExerciseData(id, input, activity, datetime, duration,
                distance, avgspeed, calories, climb, heartrate, comment);
        boolean ret = ExerciseDataStore.add(entry);

        if (ret) {
            ArrayList<ExerciseData> result = new ArrayList<>();
            result.add(entry);
            req.setAttribute("result", result);
        }

        getServletContext().getRequestDispatcher("/query.jsp").forward(
                req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doGet(req, resp);
    }

}
