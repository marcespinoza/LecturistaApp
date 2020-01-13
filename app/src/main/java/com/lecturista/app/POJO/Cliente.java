package com.lecturista.app.POJO;

import java.io.Serializable;

public class Cliente implements Serializable {

    String original_id;
    String name;
    String address;

    public String getOriginal_id() {
        return original_id;
    }

    public void setOriginal_id(String original_id) {
        this.original_id = original_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
