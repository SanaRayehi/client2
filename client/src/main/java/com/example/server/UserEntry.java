package com.example.server;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;

public class UserEntry extends HBox {
    ImageView picture;
    Label usernameLabel;
    public UserEntry(Chat chat) {
        setAlignment(Pos.CENTER_LEFT);
        picture = new ImageView(new Image(new ByteInputStream(chat.getPicture(), chat.getPicture().length)));
        picture.setFitHeight(50);
        picture.setFitWidth(50);
        picture.prefWidth(50);
        picture.prefHeight(50);
        usernameLabel = new Label(chat.getUsername());
        getChildren().addAll(picture,usernameLabel);
    }
}
