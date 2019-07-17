package com.example.server;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileTemplate extends VBox {
    SimpleDateFormat formatter = new SimpleDateFormat("MMM dd - HH:mm");
    Hyperlink message;
    Label dateLabel;
    ProgressBar progressBar;
    String path;
    public FileTemplate(String text, Date date, String path) {
        message = new Hyperlink();
        message.setWrapText(true);
        this.path = path;
        dateLabel = new Label(formatter.format(date));
        message.setText(text);
        progressBar = new ProgressBar();
        getChildren().addAll(message,progressBar,dateLabel);
    }
    public void setProgressBarValue(double value) {
        progressBar.setProgress(value);
    }

}
