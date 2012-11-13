package com.cs739.app.service;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.cs739.app.model.PlopboxImage;
import com.cs739.app.server.PMF;

public class ImageService {
    
    public static List<PlopboxImage> getAllImages() {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery("select all from Image ");
        List<PlopboxImage> results = (List<PlopboxImage>) query.execute();
        return results;
    }
    
}
