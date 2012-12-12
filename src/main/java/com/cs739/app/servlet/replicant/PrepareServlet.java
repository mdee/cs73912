package com.cs739.app.servlet.replicant;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.servlet.AbstractPlopboxServlet;
import com.cs739.app.util.AppConstants;
import com.cs739.app.util.Pair;

public class PrepareServlet extends AbstractPlopboxServlet {

    //private static final String PNG = "image/png";
    //private static final String JPG = "image/jpeg";

    /**
     * 
     */
    private static final long serialVersionUID = 3051023392056859395L;
    private static final Logger log = LoggerFactory
            .getLogger(PrepareServlet.class);

    @SuppressWarnings("static-access")
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("doGet");
        }
        //ServletContext context = request.getSession().getServletContext();
        PrintWriter out = response.getWriter();
        response.setContentType( "text/html" ); 
        out.println("<HTML>");
        if (!request.getParameterNames().hasMoreElements()) {
            out.println("<HEAD><TITLE>PrepareServlet (no args)</TITLE></HEAD>");
            out.println("<BODY>");
            out.println("<H1>PrepareServlet</H1>");
            out.println("No UserID or FileID was specified");

        }else {
            String fileId = request.getParameter(AppConstants.REQUEST_FILE_ID);
            String userId = request.getParameter(AppConstants.REQUEST_USER_ID);
            log.debug("Received a prepare request from Master");
            log.debug("Expecting a file with id: " + fileId);
            log.debug("From user; " + userId);
            Pair<String,String> pair = new Pair<String,String>(userId, fileId);
            AppConstants.OPEN_SESSION_LIST.add(pair);
            out.println("Above pair was added to acceptable sessions");
        }
        out.println("</BODY></HTML>");
        
        response.setStatus(response.SC_OK);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("doPost");
        }
        PrintWriter out = response.getWriter();
        response.setContentType( "text/html" ); 

        out.println("<HTML>");
        if (!request.getParameterNames().hasMoreElements()) {
            out.println("<HEAD><TITLE>PrepareServlet (no args)</TITLE></HEAD>");
            out.println("<BODY>");
            out.println("<H1>PrepareServlet</H1>");
            out.println("No UserID or FileID was specified");

        }else {
            Enumeration<String> paramNames = (Enumeration<String>) request.getParameterNames();
            while(paramNames.hasMoreElements()) {
                String parm = paramNames.nextElement();
                out.println(parm + " = " + request.getParameter(parm) + "<BR>"); 
            }      
        }
        out.println("</BODY></HTML>"); 	
    }

}
