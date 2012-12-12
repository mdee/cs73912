package com.cs739.app.servlet.master;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.model.PlopboxFile;
import com.cs739.app.model.Replicant;
import com.cs739.app.service.CookieService;
import com.cs739.app.service.master.PlopboxFileService;
import com.cs739.app.service.master.ReplicantService;
import com.cs739.app.servlet.AbstractPlopboxServlet;
import com.cs739.app.util.AppConstants;
import com.cs739.app.util.AppConstants.MasterPages;

public class HomeServlet extends AbstractPlopboxServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -6255601670951679699L;
    private static final Logger log = LoggerFactory
            .getLogger(HomeServlet.class);

    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Bringin up the home page");
        String username = CookieService.getCookieValueFromRequest(request, AppConstants.USERNAME);
        String userId = CookieService.getCookieValueFromRequest(request, AppConstants.USER_ID);
        try {
            if (username == null && userId == null) {
                log.debug("username nor userId were not set in cookie, redirecting");
                // User is not logged in, redirect to login page
                response.sendRedirect("login");
            } else {
                // Need to pick a replicant to set the form values, and a new file ID
                // First create a new file in anticipation of new upload
                log.debug("Creating a new file in anticipatio of upload");
                PlopboxFile newFile = new PlopboxFile(new Long(userId));
                // Save it to generate its ID
                PlopboxFileService.saveNewPlopboxFile(newFile);
                // Add this file to the Master's in-memory list of files
                log.debug("new file ID: " + newFile.getId());
                ServletContext context = request.getSession().getServletContext(); 
                List<PlopboxFile> files = (List<PlopboxFile>) context.getAttribute(AppConstants.MASTER_FILES_LIST);
                files.add(newFile);
                context.setAttribute(AppConstants.MASTER_FILES_LIST, files);
                // Now pick a replicant
                Replicant r = ReplicantService.chooseReplicantForUpload((List<Replicant>)context.getAttribute(AppConstants.REPLICANTS));
                // Send it a prepare request
                HttpClient httpClient = new DefaultHttpClient();
                String replicantUrl = "http://" + r.getHost() + ":" + r.getPort() + "/pb/prepare?userId=" + userId + "&fileId=" + newFile.getId();
                // TODO: this should be post
                HttpGet prepareRequest = new HttpGet(replicantUrl);
                HttpResponse replicantResponse = httpClient.execute(prepareRequest);
                log.debug("Here's what the replicant had to say about prepare: ");
                log.debug(replicantResponse.getStatusLine().getStatusCode() + "");
                // Set user info & file info
                request.setAttribute(AppConstants.REPLICANT_PORT, r.getPort());
                request.setAttribute(AppConstants.NEW_FILE_ID, newFile.getId());
                request.setAttribute(AppConstants.USERNAME, username);
                request.setAttribute(AppConstants.USER_ID, userId);
                forward(request, response, MasterPages.HOME.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
