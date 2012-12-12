package com.cs739.app.servlet.master;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.model.Replicant;
import com.cs739.app.service.master.ReplicantService;
import com.cs739.app.servlet.AbstractPlopboxServlet;
import com.cs739.app.util.AppConstants;

/**
 * Requests are sent to this servlet from a {@link Replicant} instance
 * when it wants to let the master know it's ready.
 * @author MDee
 *
 */
public class AddReplicantServlet extends AbstractPlopboxServlet {

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        String host = request.getRemoteHost();

        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();

        // Get the files that this replicant has
        String[] filesList = request.getParameterValues(AppConstants.REPLICANT_FILES_LIST);
        String portNumber = request.getParameter(AppConstants.REPLICANT_PORT);
        log.debug("Adding replicant...");
        log.debug(portNumber);
        
        int numReplicants = (Integer) context.getAttribute(AppConstants.NUM_REPLICANTS);
        List<Replicant> replicants = (List<Replicant>) context.getAttribute(AppConstants.REPLICANTS);
        
        // TODO: Necessary to check if this host & port are already in the master's view?
        numReplicants += 1;

        String id = ReplicantService.generateReplicantId(numReplicants);
        Replicant replicant = new Replicant(host, Integer.parseInt(portNumber), id);
        if (filesList != null){
            System.out.println(filesList[0] + "\n");
            replicant.setFiles(filesList);
        }
        log.debug("Replicant added");
        replicants.add(replicant);

        // TODO: Figure out how to parse the strings files into an IPlopboxFile thang
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
