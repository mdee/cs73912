package com.cs739.app.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

@PersistenceCapable
public class PlopboxImage {
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String name;

    @Persistent
    Blob image;

    public PlopboxImage() { }
    public PlopboxImage(String name, Blob image) {
        this.name = name; 
        this.image = image;
    }

    public Blob getImage() { 
        return image; 
    }
    
    public void setImage(Blob image) { 
        this.image = image; 
    }

}
