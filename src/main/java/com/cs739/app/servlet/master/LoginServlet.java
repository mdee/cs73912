package com.cs739.app.servlet.master;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.model.PlopboxUser;
import com.cs739.app.service.master.UserService;
import com.cs739.app.servlet.AbstractPlopboxServlet;
import com.cs739.app.util.AppConstants;
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
        // Not doing anything...
        // Check for cookies
        Cookie[] cookies = request.getCookies();
        String username = null, userId = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(AppConstants.USERNAME)) {
                    username = c.getValue();
                } else if (c.getName().equals(AppConstants.USER_ID)) {
                    userId = c.getValue();
                }
                log.debug(c.getName() + " - " + c.getValue());
            }
        }
        try {
            if (username != null && userId != null){
                response.sendRedirect("home");
            } else {
                forward(request, response, MasterPages.LOGIN.toString());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Got a POST request!");

        String username = request.getParameter(AppConstants.USERNAME);
        String password = request.getParameter("password");

        log.debug("Retrieving user w/username: " + username);
        String hashWord = UserService.hashString(password);
        PlopboxUser user = UserService.getUserWithUsername(username);
        // TODO: this might break if user is null
        if (!user.getPassword().equals(hashWord)) {
            log.error("You entered an incorrect password for the user!");
            log.debug("But it's cool, we're letting errbody in");
        }
        // Let's add a new cookie with this user's ID # in it
        Cookie idCookie = new Cookie(AppConstants.USER_ID, user.getId().toString());
        Cookie nameCookie = new Cookie(AppConstants.USERNAME, user.getUsername());
        response.addCookie(idCookie);
        response.addCookie(nameCookie);
        try {
            response.sendRedirect("home");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
