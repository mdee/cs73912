package com.cs739.app.service;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.cs739.app.model.PlopboxImage;
import com.cs739.app.server.PMF;

/**
 * Bare-bones service which queries the datastore for {@link PlopboxImage} 
 * instances.
 * @author MDee
 */
public class ImageService {
    
    public static List<PlopboxImage> getAllImages() {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery(PlopboxImage.class);
        List<PlopboxImage> results = (List<PlopboxImage>) query.execute();
        return results;
    }
    
    public static List<String> getImageIds() {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery("select fileID from " + PlopboxImage.class.getName());
        List<String> ids = (List<String>) query.execute();
        return ids;
    }
    
    public static PlopboxImage getImageWithId(String idParam) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
//        Query q = pm.newQuery(PlopboxImage.class, "id == idParam");
//        q.declareParameters("String idParam");
        PlopboxImage image = pm.getObjectById(PlopboxImage.class, idParam);
        return image;
    }
    
}
