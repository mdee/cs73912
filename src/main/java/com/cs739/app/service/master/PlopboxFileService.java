package com.cs739.app.service.master;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.cs739.app.model.PlopboxFile;
import com.cs739.app.server.PMF;
import com.cs739.app.util.AppConstants.FileState;


public class PlopboxFileService {
    
    public static void saveNewPlopboxFile(PlopboxFile file) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        pm.makePersistent(file);
        pm.close();
    }
    
    @SuppressWarnings("unchecked")
    public static List<PlopboxFile> getAllPlopboxFiles() {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery(PlopboxFile.class);
        return ((List<PlopboxFile>) query.execute());
    }
    
    public static void updatePlopboxFileState(Long fileId, FileState newState) {
        
    }
}
