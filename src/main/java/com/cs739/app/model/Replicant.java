package com.cs739.app.model;

/**
 * Simple model to represent replicating machines.
 * @author MDee
 */
public class Replicant {

    private String host;
    private int port;
    
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
    
}
