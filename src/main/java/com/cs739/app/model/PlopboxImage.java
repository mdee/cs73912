package com.cs739.app.model;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.cs739.app.util.AppConstants.FileState;
import com.google.appengine.api.datastore.Blob;

/**
 * Basic model to represent an image file.
 * @author MDee
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION) 
public class PlopboxImage implements IPlopboxFile {
    
    @PrimaryKey
    private Long id;
    
    @Persistent
    private String fileID;

    @Persistent
    private String name;

    @Persistent
    Blob data;
    
    @Persistent
    FileState state;
    
    public PlopboxImage() {}
    
    public PlopboxImage(String name, Blob data, String fileID) {
    	this.setFileID(fileID);
        this.name = name; 
        this.data = data;
        this.id = new Long(fileID);
        this.state = FileState.UPLOADED;
    }

    public FileState getState() {
        return state;
    }

    public void setState(FileState state) {
        this.state = state;
    }

    public Blob getData() { 
        return data; 
    }
    
    public void setData(Blob data) { 
        this.data = data; 
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

}
