package com.cs739.app.servlet.master;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cs739.app.model.Replicant;
import com.cs739.app.service.master.ReplicantService;
import com.cs739.app.util.AppConstants;
import com.google.gson.Gson;

/**
 * Responsible for receiving requests from client about uploading
 * a new file.
 * @author MDee
 *
 */
public class UploadServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -8809227215706883599L;
    
    @SuppressWarnings("unchecked")
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        
        HttpSession session = request.getSession();
        ServletContext context = session.getServletContext();
        List<Replicant> replicants = (List<Replicant>) context.getAttribute(AppConstants.REPLICANTS);
        
        Replicant availableReplicant = ReplicantService.chooseReplicantForUpload(replicants);
        
        Gson gson = new Gson();
        String json = gson.toJson(availableReplicant);
        
        try {
            response.getOutputStream().write(json.getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    

}
