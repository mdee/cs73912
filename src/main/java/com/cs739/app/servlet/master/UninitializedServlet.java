package com.cs739.app.servlet.master;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.util.AppConstants.MasterPages;

/**
 * If a request is received and the number of {@link Replicant} machines is not 
 * high enough (signaling an uninitialized state), it is forwarded here.
 * @author MDee
 */
public class UninitializedServlet extends HttpServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = 6416317846690901824L;
    
    private static final Logger log = LoggerFactory
            .getLogger(UninitializedServlet.class);
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        forward(request, response, MasterPages.UNINITIALIZED.toString());
    }
    
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
