package com.cs739.app.service.master;

import java.util.List;

import com.cs739.app.model.Replicant;
import com.cs739.app.util.AppConstants;

/**
 * Manages {@link Replicant} instances for the Master
 * @author MDee
 * 
 * This file has been altered.
 */
public class ReplicantService {
    
    /**
     * The client wants to upload a file, this method picks a destination.
     * @param replicants - list of {@link Replicant} instances available
     * @return a single {@link Replicant}
     */
    public static Replicant chooseReplicantForUpload(List<Replicant> replicants) {
        // Very simple right now, simply chooses the first one that's available
        Replicant replicant = null;
        for (Replicant r : replicants) {
            if (r.isAvailable()) {
                replicant = r;
            }
        }
        return replicant;
    }

    public static String generateReplicantId(int id) {
        return AppConstants.REPLICANT_ID_PREFIX + id;
    }
    
}
