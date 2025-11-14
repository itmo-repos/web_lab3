package com.lab3.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.lab3.bean.ResultsControllerBean;

@WebServlet("/getPoints")
public class GetPointsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ResultsControllerBean results = (ResultsControllerBean) getServletContext().getAttribute("resultsControllerBean");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String json = results.getJsonPoints();
        resp.getWriter().write(json);
    }
}


