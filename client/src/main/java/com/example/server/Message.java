package com.example.server;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private int chatid;
    private String username;
    private Date date;
    private String message;
    private byte[] file;
    private String fileNAme;

    public String getFileNAme() {
        return fileNAme;
    }

    public void setFileNAme(String fileNAme) {
        this.fileNAme = fileNAme;
    }

    public Message(int chatid, String username, Date date, byte[] file, String fileNAme) {
        this.chatid = chatid;
        this.username = username;
        this.date = date;
        this.file = file;
        this.fileNAme = fileNAme;
    }

    public Message(int chatid, String username, Date date, byte[] file) {
        this.chatid = chatid;
        this.username = username;
        this.date = date;
        this.file = file;
    }

    public int getChatid() {
        return chatid;
    }

    public void setChatid(int chatid) {
        this.chatid = chatid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Message(int chatid, String username, Date date, String message) {
        this.chatid = chatid;
        this.username = username;
        this.date = date;
        this.message = message;
    }
}
