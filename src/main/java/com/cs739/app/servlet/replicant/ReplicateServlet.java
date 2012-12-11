package com.cs739.app.servlet.replicant;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
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
import com.google.appengine.api.datastore.Blob;

/**
 * Requests are sent to this servlet from a {@link Replicant} instance
 * when it wants to let the master know it's ready.
 * @author MDee
 *
 */
public class ReplicateServlet extends AbstractPlopboxServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory
            .getLogger(ReplicateServlet.class);
    
    /**
     * Takes orders from the master to retreive a file from another server
     * @throws IOException 
     */
    @SuppressWarnings("unchecked")
public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    	if (log.isDebugEnabled()) {
            log.debug("doGet -- REPLICATING");
        }        
        
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        
        // Get the urls to retrieve -- only actually supports one right now
        
        Enumeration<String> paramNames = (Enumeration<String>) request.getParameterNames();

        String URL = request.getParameter("URL");
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

        nameValuePairs.add(new BasicNameValuePair("userID", request.getParameter("userID")));
        nameValuePairs.add(new BasicNameValuePair("fileID", request.getParameter("fileID")));
        nameValuePairs.add(new BasicNameValuePair("fileName", request.getParameter("fileName")));
        String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");
        URL += "?";
        URL += paramString;
        
        log.debug("on url: " + URL);
        
        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        InputStream in = null;
        try {
        	URL url = new URL(URL);
        	in = url.openStream();
        	byte[] byteChunk = new byte[4096];
        	int n;
        	
        	while( (n = in.read(byteChunk)) > 0){
        		bais.write(byteChunk, 0, n);
        	}
        	
        	 Blob imageBlob = new Blob(bais.toByteArray());
             PlopboxImage newImage = new PlopboxImage(request.getParameter("fileName"), imageBlob, request.getParameter("fileID"));
             PersistenceManager pm = PMF.get().getPersistenceManager();
             pm.makePersistent(newImage);
             pm.close();
             
        } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
        	if (in != null){
        	   in.close();
        	}
        }
        	/*HttpClient httpclient = new DefaultHttpClient();
            try {
                HttpGet httpGet = new HttpGet(URL);

                HttpResponse responseBody = httpclient.execute(httpGet);
                
                //ResponseHandler<String> responseHandler = new BasicResponseHandler();
                //String responseBody = httpclient.execute(httpPost, responseHandler);
                System.out.println("----------------------------------------");
                System.out.println(responseBody.getStatusLine());
                System.out.println("----------------------------------------");

                InputStream in = responseBody.getEntity().getContent();
                Blob imageBlob = new Blob(IOUtils.toByteArray(in));
                PlopboxImage newImage = new PlopboxImage(request.getParameter("fileName"), imageBlob, request.getParameter("fileID"));
                PersistenceManager pm = PMF.get().getPersistenceManager();
                pm.makePersistent(newImage);
                pm.close();
                
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally {
                httpclient.getConnectionManager().shutdown();
            }*/
        	
        	
       // }
   
        
        // TODO: Figure out how to parse the strings files into an IPlopboxFile thang

        
        try {
            response.getOutputStream().write("Replicant added".getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Takes orders from the master to retreive a file from another server
     * @throws IOException 
     */
    @SuppressWarnings("unchecked")
public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    	if (log.isDebugEnabled()) {
            log.debug("doPost -- REPLICATING");
        }        
        
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        
        // Get the urls to retrieve -- only actually supports one right now
        
        Enumeration<String> paramNames = (Enumeration<String>) request.getParameterNames();

        String URL = request.getParameter("URL");
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

        nameValuePairs.add(new BasicNameValuePair("userID", request.getParameter("userID")));
        nameValuePairs.add(new BasicNameValuePair("fileID", request.getParameter("fileID")));
        nameValuePairs.add(new BasicNameValuePair("fileName", request.getParameter("fileName")));
        String paramString = URLEncodedUtils.format(nameValuePairs, "utf-8");
        URL += "?";
        URL += paramString;
        
        ByteArrayOutputStream bais = new ByteArrayOutputStream();
        InputStream in = null;
        try {
        	URL url = new URL(URL);
        	in = url.openStream();
        	byte[] byteChunk = new byte[4096];
        	int n;
        	
        	while( (n = in.read(byteChunk)) > 0){
        		bais.write(byteChunk, 0, n);
        	}
        	
        	 Blob imageBlob = new Blob(IOUtils.toByteArray(in));
             PlopboxImage newImage = new PlopboxImage(request.getParameter("fileName"), imageBlob, request.getParameter("fileID"));
             PersistenceManager pm = PMF.get().getPersistenceManager();
             pm.makePersistent(newImage);
             pm.close();
             
        } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
        	if (in != null){
        	   in.close();
        	}
        }
        	/*HttpClient httpclient = new DefaultHttpClient();
            try {
                HttpGet httpGet = new HttpGet(URL);

                HttpResponse responseBody = httpclient.execute(httpGet);
                
                //ResponseHandler<String> responseHandler = new BasicResponseHandler();
                //String responseBody = httpclient.execute(httpPost, responseHandler);
                System.out.println("----------------------------------------");
                System.out.println(responseBody.getStatusLine());
                System.out.println("----------------------------------------");

                InputStream in = responseBody.getEntity().getContent();
                Blob imageBlob = new Blob(IOUtils.toByteArray(in));
                PlopboxImage newImage = new PlopboxImage(request.getParameter("fileName"), imageBlob, request.getParameter("fileID"));
                PersistenceManager pm = PMF.get().getPersistenceManager();
                pm.makePersistent(newImage);
                pm.close();
                
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally {
                httpclient.getConnectionManager().shutdown();
            }*/
        	
        	
       // }
   
        
        // TODO: Figure out how to parse the strings files into an IPlopboxFile thang

        
        try {
            response.getOutputStream().write("Replicant added".getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    

}
