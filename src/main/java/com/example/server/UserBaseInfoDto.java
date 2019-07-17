package com.example.server;

import java.io.Serializable;
import java.util.List;

public class UserBaseInfoDto implements Serializable  {
    String name;
    String lastname;
    String username;
    byte[] picture;

    List<Chat> chats;

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public UserBaseInfoDto(String name, String lastname, byte[] picture, String username) {
        this.name = name;
        this.lastname = lastname;
        this.picture = picture;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
