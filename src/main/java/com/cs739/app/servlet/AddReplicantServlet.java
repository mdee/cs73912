package com.cs739.app.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.model.Replicant;
import com.cs739.app.util.AppConstants;

/**
 * Requests are sent to this servlet from a {@link Replicant} instance
 * when it wants to let the master know it's ready.
 * @author MDee
 *
 */
public class AddReplicantServlet extends HttpServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory
            .getLogger(AddReplicantServlet.class);
    
    /**
     * Adds a new {@link Replicant} to the master's pool
     */
    @SuppressWarnings("unchecked")
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        
        String host = request.getRemoteHost();
        int port = request.getRemotePort();
        
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        
        int numReplicants = (Integer) context.getAttribute(AppConstants.NUM_REPLICANTS);
        List<Replicant> replicants = (List<Replicant>) context.getAttribute(AppConstants.REPLICANTS);
        
        Replicant replicant = new Replicant();
        replicant.setHost(host);
        replicant.setPort(port);
        
        log.debug("Replicant added");
        
        numReplicants += 1;
        replicants.add(replicant);
        
        context.setAttribute(AppConstants.NUM_REPLICANTS, numReplicants);
        context.setAttribute(AppConstants.REPLICANTS, replicants);
        
        try {
            response.getOutputStream().write("Replicant added".getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
