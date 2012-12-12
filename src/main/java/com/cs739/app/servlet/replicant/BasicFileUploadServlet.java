package com.cs739.app.servlet.replicant;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
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
import com.cs739.app.server.PMF;
import com.cs739.app.servlet.AbstractPlopboxServlet;
import com.cs739.app.util.AppConstants;
import com.cs739.app.util.Pair;
import com.google.appengine.api.datastore.Blob;

public class BasicFileUploadServlet extends AbstractPlopboxServlet {

    private static final String PNG = "image/png";
    private static final String JPG = "image/jpeg";

    /**
     * 
     */
    private static final long serialVersionUID = 3051023392056859395L;
    private static final Logger log = LoggerFactory
            .getLogger(BasicFileUploadServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("doGet");
        }
        request.setAttribute("uploadMsg", "hi dude");
        //forward(request, response, ReplicantPages.INDEX.toString());
        response.setHeader("Access-Control-Allow-Origin","*");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        log.debug("REPLICANT RECEIVED SOMETHING!");
        log.debug("doPost -- YOU IS UPLOADING");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html"); 
        ServletFileUpload upload = new ServletFileUpload();
        out.println("<HTML>");

        if (!request.getParameterNames().hasMoreElements()) {
            out.println("<HEAD><TITLE>UploadServlet (no args)</TITLE></HEAD>");
            out.println("<BODY>");
            out.println("<H1>UploadServlet</H1>");
            out.println("No UserID or FileID was specified");

        }else {

            out.println("userId" + " = " + request.getParameter("userId") + "<BR>");
            out.println("fileId" + " = " + request.getParameter("fileId") + "<BR>");
            out.println("Attempting to upload with above credentials<BR>");
            Pair<String,String> pair = new Pair<String,String>(request.getParameter("userId"), request.getParameter("fileId"));
            Iterator it = AppConstants.OPEN_SESSION_LIST.iterator();
            out.println("There are currently " + AppConstants.OPEN_SESSION_LIST.size() + " open sessions<BR>");
            log.debug("There are currently " + AppConstants.OPEN_SESSION_LIST.size() + " open sessions<BR>");
            while(it.hasNext())
            {
                String value=(String)it.next().toString();
                out.println("Value :"+value+"<BR>");
                out.println(it.equals(pair) + "<BR>");
            }
            if (!AppConstants.OPEN_SESSION_LIST.contains(pair)){
                out.println("Invalid Request, you must get authority from the master first!");
            }else{
                HttpClient httpclient = new DefaultHttpClient();
                String fileName = null;
                try {
                    log.debug("inside try");
                    FileItemIterator fileIter = upload.getItemIterator(request);
                    while (fileIter.hasNext()) {
                        log.debug("FILESS");
                        FileItemStream item = fileIter.next();

                        InputStream stream = item.openStream();
                        fileName = item.getName();
                        if (item.isFormField()) {
                            log.warn("Got a form field: " + item.getFieldName());
                        } else {
                            log.warn("Got an uploaded file: " + item.getFieldName() +
                                    ", name = " + item.getName());
                        }

                        // Is it an image?
                        if (item.getContentType().equals(PNG) || item.getContentType().equals(JPG)) {
                            byte[] bytes = IOUtils.toByteArray(stream);
                            Blob imageBlob = new Blob(bytes);
                            
                            PlopboxImage newImage = new PlopboxImage(item.getName(), imageBlob, request.getParameter("fileId"));
                            PersistenceManager pm = PMF.get().getPersistenceManager();
                            pm.makePersistent(newImage);
                            pm.close();
                        }

                    }
                    AppConstants.OPEN_SESSION_LIST.remove(pair);

                    // Prepare a post to master letting it know of success
                    HttpPost httpPost = new HttpPost("http://localhost:1234/pb/uploadComplete");

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

                    nameValuePairs.add(new BasicNameValuePair(AppConstants.REQUEST_USER_ID, request.getParameter("userId")));
                    nameValuePairs.add(new BasicNameValuePair(AppConstants.REQUEST_FILE_ID, request.getParameter("fileId")));
                    nameValuePairs.add(new BasicNameValuePair(AppConstants.REQUEST_FILE_NAME, fileName));
                    
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    String responseBody = httpclient.execute(httpPost, responseHandler);
                    log.debug("hey man");
                    System.out.println("----------------------------------------");
                    System.out.println(responseBody);
                    System.out.println("----------------------------------------");



                    out.println("Success!!<BR>");


                } catch (FileUploadException e) {
                    e.printStackTrace();
                } finally {
                    httpclient.getConnectionManager().shutdown();
                }
            }
        }
        out.println("</BODY></HTML>");
        response.setHeader("Access-Control-Allow-Origin","*");

    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin","*");
    }

}
