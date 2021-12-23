package com.example.manageractivityproject.Model;

import java.io.Serializable;

public class Abstract<T> {
    private int id;
    private String createddate;
    private String modifieddate;
    private String createdby;
    private String modifiedby;
    private String timeregistration;

    public String getTimeregistration() {
        return timeregistration;
    }

    public void setTimeregistration(String timeregistration) {
        this.timeregistration = timeregistration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(String modifieddate) {
        this.modifieddate = modifieddate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }
}
