package com.cs739.app.servlet.master;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.servlet.AbstractPlopboxServlet;
import com.cs739.app.util.AppConstants.MasterPages;

public class LoginServlet extends AbstractPlopboxServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 7330238628119085009L;
    
    private static final Logger log = LoggerFactory
            .getLogger(LoginServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // Not doin anything right now...
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Got a POST request!");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        log.debug("username: " + username);
        log.debug("password: " + password);
        
        forward(request, response, MasterPages.HOME.toString());
        
    }

}
