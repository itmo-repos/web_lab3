package com.lab3.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.lab3.bean.TimeBean;

import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/getTime")
public class TimeServlet extends HttpServlet {

    @EJB
    private TimeBean timeBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            out.print(timeBean.getCurrentTime());
        } finally {
            out.close(); // Close the PrintWriter
        }

    }
    
    
}
