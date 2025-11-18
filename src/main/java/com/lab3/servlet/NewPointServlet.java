package com.lab3.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import com.lab3.bean.ResultsControllerBean;
import com.lab3.model.AreaHitChecker;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/newPoint")
public class NewPointServlet extends HttpServlet {

    @EJB
    private ResultsControllerBean results;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/json");

        String x = request.getParameter("x_value");
        String y = request.getParameter("y_value");
        String r = request.getParameter("r_value");

        PrintWriter out = response.getWriter();
        try {
            BigDecimal xBD = new BigDecimal(x);
            BigDecimal yBD = new BigDecimal(y);
            BigDecimal rBD = new BigDecimal(r);

            String error = AreaHitChecker.validateParameters(xBD, yBD, rBD);

            if(error == null) {
                boolean hit = AreaHitChecker.checkHit(xBD, yBD, rBD);
                results.addResult(xBD, yBD, rBD, hit);
                out.println("{\"hit\":" + hit + "}");
            } else {
                out.println("{\"error\":\"" + error + "\"}");
            }
        } finally {
            out.close();
        }

    }
    
    
}
