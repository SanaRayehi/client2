package com.example.server;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class EmojiPanel extends Stage {
    Label label1 = new Label(new String(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x81}));
    Label label2 = new Label(new String(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x82}));
    Label label3 = new Label(new String(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x89}));
    Label label4 = new Label(new String(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x8B}));
    Label label5 = new Label(new String(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x8D}));
    Label label6 = new Label(new String(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x93}));
    Label label7 = new Label(new String(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x9E}));
    Label label8 = new Label(new String(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xA0}));
    Label label9 = new Label(new String(new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xA1}));
    public EmojiPanel(TextField textField){
        Label[] labels = {label1,label2,label3,label4,label5,label6,label7,label8,label9};
        HBox hBox = new HBox(2);
        hBox.getStylesheets().add(0, ClassLoader.getSystemResource("style.css").toExternalForm());
        Scene scene = new Scene(hBox,400,70);
        hBox.setAlignment(Pos.CENTER);
        for(Label label: labels) {
            label.setId("emojipanel");
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount()==2) {
                       textField.setText( textField.getText()+label.getText());
                    }
                }
            });
        }
        hBox.getChildren().addAll(labels);
        setScene(scene);
    }
}
