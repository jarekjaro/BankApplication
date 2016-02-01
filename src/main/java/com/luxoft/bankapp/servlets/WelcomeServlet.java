package com.luxoft.bankapp.servlets;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomeServlet extends HttpServlet {
    private static final long serialVersionUID = -6703624962716075443L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.println("Hello! I'm ATM <br>");
        servletOutputStream.println("<a href='/login.html'>Login</a>");
    }
}
