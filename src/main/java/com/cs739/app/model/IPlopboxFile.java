package com.cs739.app.model;

import com.google.appengine.api.datastore.Blob;

/**
 * Simple interface for Plopbox files
 * @author MDee
 *
 */
public interface IPlopboxFile {

    public Long getId();

    public void setId(Long id);

    public String getName();

    public void setName(String name);

    public Blob getData();

    public void setData(Blob data);    

}

