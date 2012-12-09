package com.cs739.app.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

/**
 * Basic model to represent an image file.
 * @author MDee
 * 
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION) 
public class PlopboxImage implements IPlopboxFile {
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String name;

    @Persistent
    Blob data;
    
    //@Persistent
    

    public PlopboxImage() {}
    
    public PlopboxImage(String name, Blob data) {
        this.name = name; 
        this.data = data;
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

}
