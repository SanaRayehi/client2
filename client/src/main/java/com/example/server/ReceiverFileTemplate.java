package com.example.server;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import java.io.IOException;
import java.util.Date;

public class ReceiverFileTemplate extends FileTemplate {
    public ReceiverFileTemplate(String text, Date date, String path) {
        super(text, date, path);
        setAlignment(Pos.CENTER_LEFT);
        message.setAlignment(Pos.CENTER_LEFT);
        message.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(path != null) {
                    try {
                        Runtime.getRuntime().exec("explorer.exe /select," + path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
