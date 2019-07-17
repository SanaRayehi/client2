package com.example.server;

import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;

import java.util.Date;

public class SenderMessageTemplate extends MessageTemplate {
    public  SenderMessageTemplate(String text, Date date) {
        super(text, date);
        setAlignment(Pos.CENTER_RIGHT);
        message.setAlignment(Pos.CENTER_RIGHT);
        message.setTextAlignment(TextAlignment.RIGHT);
    }
}
