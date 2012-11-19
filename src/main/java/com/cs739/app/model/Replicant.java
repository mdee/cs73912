package com.cs739.app.model;

import java.util.Map;
import java.util.Set;

/**
 * Simple model to represent replicating machines.
 * @author MDee
 * 
 */
public class Replicant {

    private String host;
    private int port;
    private String id;
    // Maps some sort of String ID to the actual file
    private Map<String, IPlopboxFile> filesMap;
    
    public Map<String, IPlopboxFile> getFilesMap() {
        return filesMap;
    }

    public void setFiles(Map<String, IPlopboxFile> filesMap) {
        this.filesMap = filesMap;
    }

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
