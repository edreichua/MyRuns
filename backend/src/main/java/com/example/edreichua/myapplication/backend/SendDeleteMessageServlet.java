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

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String id = req.getParameter("id");
        boolean ret = ExerciseDataStore.delete(id);

        if(ret) {
            MessagingEndpoint msg = new MessagingEndpoint();
            msg.sendMessage(id);
        }

        resp.sendRedirect("/MyRunsAppEngine.do");

        getServletContext().getRequestDispatcher("/query.jsp").forward(
                req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doGet(req, resp);
    }
}