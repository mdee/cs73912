package com.cs739.app.servlet.replicant;

import java.io.IOException;
import java.io.InputStream;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.model.PlopboxImage;
import com.cs739.app.server.PMF;
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
        response.setContentType("text/plain");
        ServletFileUpload upload = new ServletFileUpload();
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
            response.getOutputStream().write("OK!".getBytes());
        } catch (FileUploadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
