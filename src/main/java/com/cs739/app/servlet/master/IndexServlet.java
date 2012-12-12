
package com.cs739.app.servlet.master;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.model.PlopboxFile;
import com.cs739.app.model.Replicant;
import com.cs739.app.server.PMF;
import com.cs739.app.service.master.PlopboxFileService;
import com.cs739.app.servlet.AbstractPlopboxServlet;
import com.cs739.app.util.AppConstants;

/**
 * Main master endpoint right now, can handle image uploads.
 * Responsible for initializing state of server, like the number of
 * {@link Replicant} instances that have contacted.
 * @author MDee
 */
public class IndexServlet extends AbstractPlopboxServlet implements ServletContextListener {

    /**
     * 
     */
    private static final long serialVersionUID = 7252872348356932582L;

    private static final Logger log = LoggerFactory
            .getLogger(IndexServlet.class);

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        log.debug("Checking for cookie...");
        // Check for cookie, if it's set then send it home
        Cookie[] cookies = request.getCookies();
        String username = null, userId = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(AppConstants.USERNAME)) {
                    username = c.getValue();
                } else if (c.getName().equals(AppConstants.USER_ID)) {
                    userId = c.getValue();
                }
            }
        }
        if (username != null && userId != null) {
            // set them in ze cookie
            //request.setAttribute(AppConstants.USERNAME, username);
            //request.setAttribute(AppConstants.USER_ID, userId);
            response.sendRedirect("home");
        } else {
            log.debug("Cookie info is not set -- redirecting to login");
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (log.isDebugEnabled()) {
            log.debug("doPost");
        }
        response.sendRedirect("index");
    }

    ServletContext context;
    @SuppressWarnings("unchecked")
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        log.info("Context Created");
        context = contextEvent.getServletContext();
        // set up vars
        context.setAttribute(AppConstants.NUM_REPLICANTS, 0);
        context.setAttribute(AppConstants.REPLICANTS, new ArrayList<Replicant>());
        
        // GAE returns a read-only List, so copy it to a modifiable one
        // Hack around PM exceptions...
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery(PlopboxFile.class);
        List<PlopboxFile> persistedFiles = (List<PlopboxFile>) query.execute(); 
        //log.debug(persistedFiles.size() + "");
        List<PlopboxFile> filesCopy = new ArrayList<PlopboxFile>(persistedFiles);
        context.setAttribute(AppConstants.MASTER_FILES_LIST, filesCopy);
        
        // Keep a map of in-progress file Ids to replicants
        Map<Long, Replicant> fileProgressReplicantMap = new HashMap<Long, Replicant>();
        context.setAttribute(AppConstants.IN_PROGRESS_FILE_REPL_MAP, fileProgressReplicantMap);
        // Close the PM
        pm.close();
    }
    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        context = contextEvent.getServletContext();
        log.info("Context Destroyed");
    }
}
