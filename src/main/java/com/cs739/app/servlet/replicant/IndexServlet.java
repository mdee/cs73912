
package com.cs739.app.servlet.replicant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.cs739.app.util.AppConstants.ReplicantPages;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;



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
        forward(request, response, ReplicantPages.INDEX.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (log.isDebugEnabled()) {
            log.debug("doPost");
        }
        // TODO: Is this necessary?
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
        List<String> fileIDs = ImageService.getImageIds();
        
        context.setAttribute(AppConstants.REPLICANT_FILES_LIST, fileIDs);
        // start up replicant
        HttpClient httpclient = new DefaultHttpClient();
        try {
        	HttpPost httpPost = new HttpPost("http://localhost:1234/pb/addReplicant");
        	//MultipartEntity entity = new MultipartEntity();
        	
        	Iterator it=fileIDs.iterator();
            System.out.println("There are currently " + fileIDs.size() + " files on this server");
            if (fileIDs.size() > 0){
            	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(fileIDs.size());
            
            	while(it.hasNext()){
            		String value = (String)it.next();
            		System.out.println("Value :"+value+"\n");
            		nameValuePairs.add(new BasicNameValuePair(AppConstants.REPLICANT_FILES_LIST, value));
            	}
            	httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            }
        	
        	ResponseHandler<String> responseHandler = new BasicResponseHandler();
        	String responseBody = httpclient.execute(httpPost, responseHandler);
        	System.out.println("----------------------------------------");
            System.out.println(responseBody);
            System.out.println("----------------------------------------");
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
        	httpclient.getConnectionManager().shutdown();
        }

    }
    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        context = contextEvent.getServletContext();
        log.info("Context Destroyed");
    }
}
