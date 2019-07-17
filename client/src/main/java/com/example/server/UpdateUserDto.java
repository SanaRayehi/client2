package com.example.server;

import java.io.Serializable;

public class UpdateUserDto implements Serializable {
    private String username;
    private String name;
    private String lastname;
    private byte[] image;


    public UpdateUserDto(String username, String name, String lastname, byte[] image) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
