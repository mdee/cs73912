package com.cs739.app.service.master;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.cs739.app.model.PlopboxUser;
import com.cs739.app.server.PMF;

public class UserService {

    public static String hashString(String string) {
        String returnString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] stringBytes = string.getBytes("UTF-8");
            byte[] md5Digest = md.digest(stringBytes);
            returnString = new String(md5Digest, "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return returnString;
    }

    @SuppressWarnings("unchecked")
    public static PlopboxUser getUserWithUsername(String usernameParam) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Query q = pm.newQuery(PlopboxUser.class, "username == usernameParam");
        q.declareParameters("String usernameParam");
        Object o = q.execute(usernameParam);
        // TODO: this might break if the list of results is empty
        PlopboxUser user = ((List<PlopboxUser>) q.execute(usernameParam)).get(0);
        return user;
    }
    
    public static void saveNewPlopboxUser(PlopboxUser newUser) {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        pm.makePersistent(newUser);
        pm.close();
    }
    
}
