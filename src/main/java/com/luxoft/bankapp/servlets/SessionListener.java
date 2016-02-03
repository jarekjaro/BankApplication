package com.luxoft.bankapp.servlets;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    static Integer clientsConnected = new Integer(0);

    public static Integer getClientsConnected() {
        return clientsConnected;
    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        final ServletContext context = httpSessionEvent.getSession().getServletContext();
        synchronized (SessionListener.class) {
            clientsConnected = (Integer) context.getAttribute("clientsConnected");
            if (clientsConnected == null) {
                clientsConnected = 1;
            } else {
                clientsConnected++;
            }
            context.setAttribute("clientsConnected", clientsConnected);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        final ServletContext context = httpSessionEvent.getSession().getServletContext();
        synchronized (SessionListener.class) {
            clientsConnected = (Integer) context.getAttribute("clientsConnected");
            if (clientsConnected == null) {
                clientsConnected = 0;
            } else {
                clientsConnected--;
            }
            context.setAttribute("clientsConnected", clientsConnected);
        }
    }
}
