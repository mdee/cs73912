package com.cs739.app.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to hold constants. Most of them are 
 * likely keys to access attributes within the servlet context.
 * @author MDee
 *
 */
public class AppConstants {

    public static final String NUM_REPLICANTS = "NUM_REPLICANTS";
    
    public static final String ADD_REPLICANT_URI = "pb/addReplicant";
    
    public static final String REPLICANTS = "REPLICANTS";
    
    public static final String REPLICANT_FILES_LIST = "REPLICANT_FILES_LIST";
    
    public static final List<Pair<String, String>> OPEN_SESSION_LIST = new ArrayList<Pair<String, String> >();
    
    public static enum ReplicantState {
        AVAILABLE,
        BUSY,
        UNAVAILABLE;
    };
    
    public static final String REPLICANT_ID_PREFIX = "R_";
}
