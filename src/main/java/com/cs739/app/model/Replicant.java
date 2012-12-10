package com.cs739.app.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


import com.cs739.app.util.AppConstants.ReplicantState;

/**
 * Simple model to represent replicating machines.
 * @author MDee
 * 
 */
public class Replicant implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3003002870435119600L;
    
    private String host;
    private int port;
    private String id;
    private ReplicantState state;
    private List<String> files;
    
    public Replicant(String host, int port, String id) {
        this.host = host;
        this.port = port;
        this.id = id;
        this.state = ReplicantState.AVAILABLE;
        this.files = new ArrayList<String>();
    }
    
    // Maps some sort of String ID to the actual file
    private Map<String, IPlopboxFile> filesMap;
    
    public Map<String, IPlopboxFile> getFilesMap() {
        return filesMap;
    }
    
    public boolean isAvailable() {
        return this.state == ReplicantState.AVAILABLE;
    }
    
    public boolean isBusy() {
        return this.state == ReplicantState.BUSY;
    }
    
    public boolean isUnavailable() {
        return this.state == ReplicantState.UNAVAILABLE;
    }
    
    public void setState(ReplicantState state) {
        this.state = state;
    }

    //public void setFiles(Map<String, IPlopboxFile> filesMap) {
       // this.filesMap = filesMap;
    //}
    
    public void setFiles(String[] files){
    	this.files.addAll(Arrays.asList(files));
    }
    
    // need helper functions for finding files etc

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public int getPort() {
        return port;
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public void addFile(String key, IPlopboxFile file) {
        if (filesMap.containsKey(key)) {
            throw new IllegalArgumentException("File already exists on this replicant");
        } else {
            filesMap.put(key, file);
        }
    } 
    
    public void removeFile(String key) {
        if (!filesMap.containsKey(key)) {
            throw new IllegalArgumentException("Files does not exist on this replicant");
        } else {
            filesMap.remove(key);
        }
        
    }
}
