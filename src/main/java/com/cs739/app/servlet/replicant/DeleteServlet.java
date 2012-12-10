package com.cs739.app.servlet.replicant;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.model.PlopboxImage;
import com.cs739.app.model.Replicant;
import com.cs739.app.server.PMF;
import com.cs739.app.util.*;
import com.google.appengine.api.datastore.Blob;

public class DeleteServlet extends HttpServlet {

    private static final String PNG = "image/png";
    private static final String JPG = "image/jpeg";
    
    /**
     * 
     */
    private static final long serialVersionUID = 3051023392056859395L;
    private static final Logger log = LoggerFactory
            .getLogger(DeleteServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("doGet");
        }
        PrintWriter out = response.getWriter();
        response.setContentType( "text/html" ); 
        out.println("<HTML>");
        if (!request.getParameterNames().hasMoreElements()) {
        	out.println("<HEAD><TITLE>DeleteeServlet (no args)</TITLE></HEAD>");
            out.println("<BODY>");
            out.println("<H1>DeleteServlet</H1>");
            out.println("No UserID or FileID was specified");

        }else {
        	java.util.Enumeration paramNames = request.getParameterNames();
            String userID = (String)paramNames.nextElement();
            String fileID = (String)paramNames.nextElement();
            out.println(userID + " = " + request.getParameter("userID") + "<BR>");
            out.println(fileID + " = " + request.getParameter("fileID") + "<BR>");
            out.println("Attempting to delete with above credentials<BR>");
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
            	PersistenceManager pm = PMF.get().getPersistenceManager();
		        try {
		            PlopboxImage image = pm.getObjectById(PlopboxImage.class, request.getParameter("fileID"));
		            pm.deletePersistent(image);
		            AppConstants.OPEN_SESSION_LIST.remove(pair);
		            out.println("Deleted!<BR>");
		        } finally {
		        	pm.close();
		        }
            }
        }
        out.println("</BODY></HTML>");
        
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("doPost -- YOU IS DELETING");
        }
        
        forward(request, response, "index.jsp");
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
