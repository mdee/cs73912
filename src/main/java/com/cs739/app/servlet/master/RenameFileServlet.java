package com.cs739.app.servlet.master;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cs739.app.model.PlopboxFile;
import com.cs739.app.service.master.PlopboxFileService;
import com.cs739.app.servlet.AbstractPlopboxServlet;
import com.cs739.app.util.AppConstants;

public class RenameFileServlet extends AbstractPlopboxServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -5437684185773604938L;
    private static final Logger log = LoggerFactory
            .getLogger(RenameFileServlet.class);
    
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        log.debug("rename servlet do post");
        log.debug("Values sent in:");
        String key = request.getParameter(AppConstants.RENAME_FILE_KEY);
        String pk = request.getParameter(AppConstants.RENAME_FILE_PK);
        String value = request.getParameter(AppConstants.RENAME_FILE_VALUE);
        
        log.debug("KEY: " + key);
        log.debug("PK: " + pk);
        log.debug("VAL: " + value);
        
        // Rename the file, yo
        ServletContext context = request.getSession().getServletContext();
        List<PlopboxFile> masterFiles = (List<PlopboxFile>) context.getAttribute(AppConstants.MASTER_FILES_LIST);
        for (PlopboxFile file : masterFiles) {
            if (file.getId().toString().equals(pk)) {
                log.debug("Found file to rename");
                file.setName(value);
                PlopboxFileService.updatePlopboxFile(file);
                break;
            }
        }
        
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("DO GET RENAME FILE SERVLIT");
    }
}
