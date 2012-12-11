package com.cs739.app.servlet.replicant;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.model.PlopboxImage;
import com.cs739.app.model.Replicant;
import com.cs739.app.server.PMF;
import com.cs739.app.service.master.ReplicantService;
import com.cs739.app.servlet.AbstractPlopboxServlet;
import com.cs739.app.util.AppConstants;
import com.cs739.app.util.Pair;
import com.google.appengine.api.datastore.Blob;

/**
 * Requests are sent to this servlet from a {@link Replicant} instance
 * when it wants to let the master know it's ready.
 * @author MDee
 *
 */
public class GetServlet extends AbstractPlopboxServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory
            .getLogger(GetServlet.class);
    
    /**
     * Adds a new {@link Replicant} to the master's pool
     * @throws IOException 
     */
    @SuppressWarnings("unchecked")
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
    	 //PrintWriter out = response.getWriter();
         response.setContentType( "text/html" ); 
        
         
         if (!request.getParameterNames().hasMoreElements()) {
             System.out.println("No UserID or FileID was specified");

         }else {
             Enumeration<String> paramNames = (Enumeration<String>) request.getParameterNames();
             String userID = paramNames.nextElement();
             String fileID = paramNames.nextElement();

             Pair pair = new Pair(request.getParameter("userID"), request.getParameter("fileID"));


             if (!AppConstants.OPEN_SESSION_LIST.contains(pair)){
                 System.out.println("Invalid Request, you must get authority from the master first!");
             }else{
            	 PersistenceManager pm = PMF.get().getPersistenceManager();
 		        try {
	 		        PlopboxImage image = pm.getObjectById(PlopboxImage.class, new Long(request.getParameter("fileID")));
	 		        ServletOutputStream output = response.getOutputStream();
	 		        
	 		       Blob imageBlob = image.getData();
	 		       output.write(imageBlob.getBytes());     
	            
 		            AppConstants.OPEN_SESSION_LIST.remove(pair);
 		            System.out.println("Returned an image!<BR>");    
                 }catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             }
         }

     }
}

