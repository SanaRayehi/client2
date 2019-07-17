package com.example.server;

import java.io.Serializable;
import java.util.Date;

public class UserStatusDto implements Serializable {
    Date date;
    boolean status;

    public UserStatusDto(Date date, boolean status) {
        this.date = date;
        this.status = status;
    }

    public UserStatusDto(boolean status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
