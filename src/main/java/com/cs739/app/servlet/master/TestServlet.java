package com.cs739.app.servlet.master;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.servlet.AbstractPlopboxServlet;
import com.cs739.app.util.AppConstants;
import com.cs739.app.util.AppConstants.MasterPages;

public class TestServlet extends AbstractPlopboxServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 7330238628119085009L;

    private static final Logger log = LoggerFactory
            .getLogger(TestServlet.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("Bringing up the home page");
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
        // Set the cookiez?
        request.setAttribute(AppConstants.USERNAME, username);
        request.setAttribute(AppConstants.USER_ID, userId);
        forward(request, response, MasterPages.TEST.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        // Not doing anything
    }

}
