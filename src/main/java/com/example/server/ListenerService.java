package com.example.server;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class ListenerService extends Task {
    InputStream inputStream;
    VBox pane;
    ScrollPane scrollPane;
    String username;
    int chatId;
    Label label;
    public ListenerService(InputStream inputStream, VBox pane, ScrollPane scrollPane, String username, int chatId, Label label) {
        this.inputStream = inputStream;
        this.pane = pane;
        this.scrollPane = scrollPane;
        this.username = username;
        this.chatId = chatId;
        this.label = label;
    }

    @Override
    protected Object call() throws Exception {
        while (true) {
            byte[] buffer = new byte[8192];
            int size = 0;
            try {
                size = inputStream.read(buffer);
            } catch (IOException e) {
                System.out.println("client disconnected");
                return null;
            }
            if(size==-1) {
                return null;
            }
            String message = new String(buffer,0,size);
            System.out.println(message);
            char startChar = message.charAt(0);
            final String mess = message.substring(1);
            if (startChar=='m') {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
//                        mess = MessageCipher.decryptMessage(mess);
                        SenderMessageTemplate senderMessageTemplate = new SenderMessageTemplate(mess, new Date());
                        senderMessageTemplate.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
                        pane.getChildren().add(senderMessageTemplate);
                    }
                });
            }
            else if (startChar=='s'){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        int indexOfPar = label.getText().indexOf("(");
                        label.setText(label.getText().substring(0,indexOfPar)+"("+mess+")");
                    }
                });
            }
            else {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        SenderFileTemplate senderMessageTemplate = new SenderFileTemplate(mess, new Date(), null, username, chatId );
                        senderMessageTemplate.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
                        pane.getChildren().add(senderMessageTemplate);
                    }
                });
            }
        }
    }
}
