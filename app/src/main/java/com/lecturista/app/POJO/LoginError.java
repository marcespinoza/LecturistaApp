package com.lecturista.app.POJO;

import com.google.gson.annotations.SerializedName;

public class LoginError {

    String status;
    @SerializedName("status-msg")
    String statusmessage;

    public String getStatusmessage() {
        return statusmessage;
    }

    public void setStatusmessage(String statusmessage) {
        this.statusmessage = statusmessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
