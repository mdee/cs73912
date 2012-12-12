package com.cs739.app.servlet.master;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.servlet.AbstractPlopboxServlet;

/**
 * Responsible for receiving requests from client about uploading
 * a new file.
 * @author MDee
 *
 */
public class UploadServlet extends AbstractPlopboxServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -8809227215706883599L;
    
    private static final Logger log = LoggerFactory
            .getLogger(UploadServlet.class);
    
    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("DO GET");
        
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        
//        List<Replicant> replicants = (List<Replicant>) context.getAttribute(AppConstants.REPLICANTS);
//        
//        Replicant availableReplicant = ReplicantService.chooseReplicantForUpload(replicants);
//        
//        Gson gson = new Gson();
//        String json = gson.toJson(availableReplicant);
        
//        try {
//            response.getOutputStream().write(json.getBytes());
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        response.setHeader("Access-Control-Allow-Origin","*");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        log.debug("DO POST");
        response.setHeader("Access-Control-Allow-Origin","*");
        ServletFileUpload upload = new ServletFileUpload();
        
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost("http://localhost:9966/pb/upload");
        
        try {
            StringEntity input = new StringEntity("{\"qty\":100,\"name\":\"iPad 4\"}");
            input.setContentType("application/json");
            postRequest.setEntity(input);
            HttpResponse repResponse = httpClient.execute(postRequest);
            
            if (repResponse.getStatusLine().getStatusCode() != 201) {
                throw new RuntimeException("Failed : HTTP error code : "
                    + repResponse.getStatusLine().getStatusCode());
            }
     
            BufferedReader br = new BufferedReader(
                            new InputStreamReader((repResponse.getEntity().getContent())));
     
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
     
            httpClient.getConnectionManager().shutdown();
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //post.setRequestBody(arg0);
        
        //NameValuePair[] pairs = {new NameValuePair("key", "")};
        
        //
        
        
        try {
            FileItemIterator fileIter = upload.getItemIterator(request);
            while (fileIter.hasNext()) {
                FileItemStream item = fileIter.next();
                InputStream stream = item.openStream();
                if (item.isFormField()) {
                    log.warn("Got a form field: " + item.getFieldName());
                } else {
                    log.warn("Got an uploaded file: " + item.getFieldName() +
                            ", name = " + item.getName());
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        log.debug("DO OPTIONS");
        response.setHeader("Access-Control-Allow-Origin","*");
    }
}
