package com.luxoft.bankapp.servlets;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = -4989841998376730182L;
    private static Logger logger = Logger.getLogger(LoginServlet.class.getCanonicalName());

    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String clientName = request.getParameter("clientName");
        System.out.println(clientName);
        if (clientName == null) {
            logger.warn("Client not found");
            throw new ServletException("No client specified.");
        }
        request.getSession().setAttribute("clientName", clientName);
        logger.info("Client " + clientName + " logged into ATM");
        response.sendRedirect("/menu.html");
    }
}
