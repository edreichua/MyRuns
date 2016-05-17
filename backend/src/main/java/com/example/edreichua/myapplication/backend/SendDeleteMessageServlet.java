package com.example.edreichua.myapplication.backend;

import com.example.edreichua.myapplication.backend.data.ExerciseDataStore;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by edreichua on 5/14/16.
 */
public class SendDeleteMessageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Standard doGet
     * @param req
     * @param resp
     * @throws IOException
     * @throws ServletException
     */
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        // Retrieve id to be deleted
        String id = req.getParameter("id");

        // Delete entity from datastore based on id
        boolean ret = ExerciseDataStore.delete(id);

        // Send message to client to request for deletion
        if(ret) {
            MessagingEndpoint msg = new MessagingEndpoint();
            msg.sendMessage(id);
        }

        // Redirect request to MyRunsAppEngineServlet
        resp.sendRedirect("/MyRunsAppEngine.do");
    }

    /**
     * doPost to call doGet
     * @param req
     * @param resp
     * @throws IOException
     * @throws ServletException
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doGet(req, resp);
    }
}