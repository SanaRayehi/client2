package com.example.server;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MessageTemplate extends VBox {
    SimpleDateFormat formatter = new SimpleDateFormat("MMM dd - HH:mm");
    Label message;
    Label dateLabel;
    public MessageTemplate(String text, Date date) {
        this.getStylesheets().add(0, ClassLoader.getSystemResource("style.css").toExternalForm());
        message = new Label();
        message.setId("emoji");
        message.setWrapText(true);
        dateLabel = new Label(formatter.format(date));
        message.setText(text);
//        Random random = new Random();
//        if(random.nextBoolean()) {
//            byte[] emojiBytes = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x81};
//            String emojiAsString = new String(emojiBytes, Charset.forName("UTF-8"));
//            message.setText(emojiAsString);
//        }
        getChildren().addAll(message,dateLabel);
    }

}
