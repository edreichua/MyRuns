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

        int id = Integer.parseInt(req.getParameter("id"));
        ExerciseDataStore.delete(id);
        resp.sendRedirect("/query.do");

        boolean ret = ExerciseDataStore.delete(id);
        if (ret) {
            req.setAttribute("_retStr", "Entry " + id + " successfully deleted");

            MessagingEndpoint msg = new MessagingEndpoint();
            msg.sendMessage(Long.toString(id));

        }
        else {
            req.setAttribute("_retStr", id + " does not exist");
        }

        getServletContext().getRequestDispatcher("/query.jsp").forward(
                req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doGet(req, resp);
    }
}