package com.cs739.app.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieService {
    
    public static Cookie createNewCookie(String name, String value) {
        Cookie newCookie = new Cookie(name, value);
        // This means that once you close the tab your session is done-skee
        newCookie.setMaxAge(-1);
        return newCookie;
    }

    public static String getCookieValueFromRequest(HttpServletRequest request, String name) {
        String returnVal = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    returnVal = c.getValue();
                    break;
                }
            }
        }
        return returnVal;
    }
}
