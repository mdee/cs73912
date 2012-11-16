package com.cs739.app.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class PMF {

    private static final Logger log = LoggerFactory
            .getLogger(PMF.class);
    
    private static final PersistenceManagerFactory pmfInstance =
            JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {}

    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }

}
