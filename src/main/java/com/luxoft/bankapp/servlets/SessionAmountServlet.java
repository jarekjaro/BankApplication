package com.luxoft.bankapp.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionAmountServlet extends HttpServlet {
    private static final long serialVersionUID = -2450078375435782129L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletContext context = getServletContext();
        String connectedClients = (String) context.getAttribute("connectedClients");
        response.setContentType("text/html");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        ServletOutputStream out = response.getOutputStream();
        response.sendRedirect("sessions.jsp");
//        out.println("<!DOCTYPE html>");
//        out.println("<html><body>Sessions: ");
//        out.println(SessionListener.clientsConnected);
//        out.println("</body></html>");
    }
}
