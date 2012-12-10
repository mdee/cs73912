package com.cs739.app.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Plopbox servlets will sub-class this guy for common functionality
 * @author MDee
 *
 */
public abstract class AbstractPlopboxServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 2841091743488947429L;
    private static final Logger log = LoggerFactory
            .getLogger(AbstractPlopboxServlet.class);

    /**
     * Forwards request and response to given path. Handles any exceptions
     * caused by forward target by printing them to logger.
     * @param request 
     * @param response
     * @param path 
     */
    protected void forward(HttpServletRequest request,
            HttpServletResponse response, String path) {
        try {
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.forward(request, response);
        } catch (Throwable tr) {
            if (log.isErrorEnabled()) {
                log.error("Cought Exception: " + tr.getMessage());
                log.debug("StackTrace:", tr);
            }
        }
    }

}
