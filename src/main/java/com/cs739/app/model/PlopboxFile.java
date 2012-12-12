package com.cs739.app.model;

import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.cs739.app.util.AppConstants.FileState;
import com.google.appengine.api.datastore.Blob;

@PersistenceCapable(identityType = IdentityType.APPLICATION) 
public class PlopboxFile {
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    
    @Persistent
    private String fileID;

    @Persistent
    private String name;

    @Persistent
    Blob data;
    
    @Persistent
    private Long ownerId;
    
    @Persistent
    private List<Long> viewerIds;

    public PlopboxFile() {}
    
    public PlopboxFile(Long ownerId) {
        this.ownerId = ownerId;
        this.state = FileState.INITIALIZED;
    }
    
    
    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public List<Long> getViewerIds() {
        return viewerIds;
    }

    public void setViewerIds(List<Long> viewerIds) {
        this.viewerIds = viewerIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileID() {
        return fileID;
    }

    public void setFileID(String fileID) {
        this.fileID = fileID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Blob getData() {
        return data;
    }

    public void setData(Blob data) {
        this.data = data;
    }

    public FileState getState() {
        return state;
    }

    public void setState(FileState state) {
        this.state = state;
    }

    @Persistent
    FileState state;

}
