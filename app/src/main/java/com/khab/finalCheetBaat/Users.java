package com.khab.finalCheetBaat;


public class Users extends SuperUser implements SuperClassInterface{
    public String name, status;
    public Users(){

    }

    public Users(String name, String status) {
        super();
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
