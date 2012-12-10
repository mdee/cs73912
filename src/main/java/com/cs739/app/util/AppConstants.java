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

    // Stuff for markup
    public static final String JSP_FOLDER = "pb";
    
    public static final String MASTER_JSP = "m/";
    
    // This points to the JSP folder for replicants ('/pb/r') right now
    public static final String REPLICANT_JSP = "r/";
    
    public static final String ADD_REPLICANT_URI = JSP_FOLDER + "/addReplicant";
    
    // Keys for data in the servlet context
    public static final String NUM_REPLICANTS = "NUM_REPLICANTS";
    
    public static final String REPLICANTS = "REPLICANTS";
    
    public static final String REPLICANT_FILES_LIST = "REPLICANT_FILES_LIST";
    
    public static final List<Pair<String, String> > OPEN_SESSION_LIST = new ArrayList<Pair<String, String> >();
    
    public static enum ReplicantState {
        AVAILABLE,
        BUSY,
        UNAVAILABLE;
    };
    
    public static enum ReplicantPages {
        INDEX {
            public String toString() {
                return REPLICANT_JSP + "index.jsp";
            }
        },
        VIEW_IMAGE {
            public String toString() {
                return REPLICANT_JSP + "viewImage.jsp";
            }
        }
    }
    
    public static enum MasterPages {
        INDEX {
            public String toString() {
                return MASTER_JSP + "index.jsp";
            }
        },
        UNINITIALIZED {
            public String toString() {
                return MASTER_JSP + "uninitialized.jsp";
            }
        }
    }
    
    public static final String REPLICANT_ID_PREFIX = "R_";
}
