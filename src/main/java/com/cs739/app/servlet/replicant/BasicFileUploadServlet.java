package com.cs739.app.servlet.replicant;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.model.PlopboxImage;
import com.cs739.app.server.PMF;
import com.cs739.app.util.*;
import com.google.appengine.api.datastore.Blob;

public class BasicFileUploadServlet extends HttpServlet {

    private static final String PNG = "image/png";
    private static final String JPG = "image/jpeg";
    
    /**
     * 
     */
    private static final long serialVersionUID = 3051023392056859395L;
    private static final Logger log = LoggerFactory
            .getLogger(BasicFileUploadServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("doGet");
        }
        request.setAttribute("uploadMsg", "hi dude");
        forward(request, response, "index.jsp");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("doPost -- YOU IS UPLOADING");
        }
        PrintWriter out = response.getWriter();
        response.setContentType( "text/html" ); 
        ServletFileUpload upload = new ServletFileUpload();
        out.println("<HTML>");
        if (!request.getParameterNames().hasMoreElements()) {
        	out.println("<HEAD><TITLE>PrepareServlet (no args)</TITLE></HEAD>");
            out.println("<BODY>");
            out.println("<H1>PrepareServlet</H1>");
            out.println("No UserID or FileID was specified");

        }else {
        	java.util.Enumeration paramNames = request.getParameterNames();
            String userID = (String)paramNames.nextElement();
            String fileID = (String)paramNames.nextElement();
            out.println(userID + " = " + request.getParameter("userID") + "<BR>");
            out.println(fileID + " = " + request.getParameter("fileID") + "<BR>");
            out.println("Attempting to upload with above credentials<BR>");
            Pair pair = new Pair(request.getParameter("userID"), request.getParameter("fileID"));
            Iterator it=AppConstants.OPEN_SESSION_LIST.iterator();
            out.println("There are currently " + AppConstants.OPEN_SESSION_LIST.size() + " open sessions<BR>");
            while(it.hasNext())
            {
              String value=(String)it.next().toString();

              out.println("Value :"+value+"<BR>");
              out.println(it.equals(pair) + "<BR>");
            }
            if (!AppConstants.OPEN_SESSION_LIST.contains(pair)){
            	out.println("Invalid Request, you must get authority from the master first!");
            }else{
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
		                
		                // Is it an image?
		                if (item.getContentType().equals(PNG) || item.getContentType().equals(JPG)) {
		                    Blob imageBlob = new Blob(IOUtils.toByteArray(stream));
		                    PlopboxImage newImage = new PlopboxImage(item.getName(), imageBlob);
		                    PersistenceManager pm = PMF.get().getPersistenceManager();
		                    pm.makePersistent(newImage);
		                    pm.close();
		                }
		                // This code iterates over dem bytes
		                //int len;
		                //byte[] buffer = new byte[8192];
		                //while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
		                //  response.getOutputStream().write(buffer, 0, len);
		                //}
		            }
		            AppConstants.OPEN_SESSION_LIST.remove(pair);
		            out.println("Success!!<BR>");
		        } catch (FileUploadException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
            }
        }
        out.println("</BODY></HTML>");
    }

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
