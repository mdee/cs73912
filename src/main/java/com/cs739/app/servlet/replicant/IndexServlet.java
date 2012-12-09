
package com.cs739.app.servlet.replicant;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.model.Replicant;
import com.cs739.app.service.ImageService;
import com.cs739.app.util.AppConstants;

/**
 * Main master endpoint right now, can handle image uploads.
 * Responsible for initializing state of server, like the number of
 * {@link Replicant} instances that have contacted.
 * @author MDee
 */
public class IndexServlet extends HttpServlet implements ServletContextListener {

    /**
     * 
     */
    private static final long serialVersionUID = 7252872348356932582L;

    private static final Logger log = LoggerFactory
    .getLogger(IndexServlet.class);

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        if (log.isDebugEnabled()) {
            log.debug("doGet");
        }
        System.out.println("HEY DUDE");
        request.setAttribute("images", ImageService.getAllImages());
        request.setAttribute("uploadMsg", "hi dude");
        forward(request, response, "index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (log.isDebugEnabled()) {
            log.debug("doPost");
        }
        response.sendRedirect("index");
    }

    /**
     * Forwards request and response to given path. Handles any exceptions
     * caused by forward target by printing them to logger.
     * 
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

    ServletContext context;
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        log.info("Context Created");
        context = contextEvent.getServletContext();
        // set up replicant vars
        context.setAttribute(AppConstants.NUM_REPLICANTS, 0);
        context.setAttribute(AppConstants.REPLICANTS, new ArrayList<Replicant>());
       
    }
    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        context = contextEvent.getServletContext();
        log.info("Context Destroyed");
    }
}
