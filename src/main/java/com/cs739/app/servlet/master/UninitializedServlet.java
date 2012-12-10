package com.cs739.app.servlet.master;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.servlet.AbstractPlopboxServlet;
import com.cs739.app.util.AppConstants.MasterPages;

/**
 * If a request is received and the number of {@link Replicant} machines is not 
 * high enough (signaling an uninitialized state), it is forwarded here.
 * @author MDee
 */
public class UninitializedServlet extends AbstractPlopboxServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = 6416317846690901824L;
    
    private static final Logger log = LoggerFactory
            .getLogger(UninitializedServlet.class);
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Not enough replicants to start servin suckas...");
        forward(request, response, MasterPages.UNINITIALIZED.toString());
    }
    
}
