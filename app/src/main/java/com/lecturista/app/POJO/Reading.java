package com.lecturista.app.POJO;

import java.util.Date;

public class Reading {

    String id_reqrite;
    String lecturista_id;
    String affiliate_id;
    String name;
    String addres;
    String reading;
    Date created_at;

    public String getId_reqrite() {
        return id_reqrite;
    }

    public void setId_reqrite(String id_reqrite) {
        this.id_reqrite = id_reqrite;
    }

    public String getLecturista_id() {
        return lecturista_id;
    }

    public void setLecturista_id(String lecturista_id) {
        this.lecturista_id = lecturista_id;
    }

    public String getAffiliate_id() {
        return affiliate_id;
    }

    public void setAffiliate_id(String affiliate_id) {
        this.affiliate_id = affiliate_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
