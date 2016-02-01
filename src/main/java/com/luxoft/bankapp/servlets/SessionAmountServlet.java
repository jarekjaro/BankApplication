package com.luxoft.bankapp.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionAmountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String connectedClients = (String) context.getAttribute("connectedClients");
        response.setContentType("text/html");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.println("Sessions: "+ connectedClients);
    }
}
