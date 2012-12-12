package com.cs739.app.service.master;

import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.cs739.app.model.PlopboxFile;
import com.cs739.app.model.Replicant;
import com.cs739.app.server.PMF;


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
    
    public static void updatePlopboxFile(PlopboxFile file) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        pm.makePersistent(file);
        pm.close();
    }
    
    public static void addInProgressFileToMap(Long fileId, Replicant r, Map<Long, Replicant> map) {
        map.put(fileId, r);
    }
    
    public static void removeInProgressFileFromMap(Long fileId, Map<Long, Replicant> map) {
        map.remove(fileId);
    }
}
