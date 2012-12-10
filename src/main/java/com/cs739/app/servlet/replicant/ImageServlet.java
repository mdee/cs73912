package com.cs739.app.servlet.replicant;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cs739.app.model.PlopboxImage;
import com.cs739.app.service.ImageService;

public class ImageServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -5570824545191958079L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
    	
        // Assuming that a GET request is for a particular image to be displayed in browser
        // TODO: this might blow up if a null value is returned by getParameter
        Long id = new Long(request.getParameter("id"));
        PlopboxImage image = ImageService.getImageWithId(id); 
        response.setContentType("image/png");
        try {
            response.getOutputStream().write(image.getData().getBytes());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
