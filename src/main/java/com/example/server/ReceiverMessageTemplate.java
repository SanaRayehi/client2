package com.example.server;

import javafx.geometry.Pos;

import java.util.Date;

public class ReceiverMessageTemplate extends MessageTemplate {
    public ReceiverMessageTemplate(String text, Date date) {
        super(text, date);
        setAlignment(Pos.CENTER_LEFT);
        message.setAlignment(Pos.CENTER_LEFT);
    }
}
