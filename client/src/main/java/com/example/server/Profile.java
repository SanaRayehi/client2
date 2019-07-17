package com.example.server;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

public class Profile extends Stage {

    Label name;
    Label lastname;
    Label username;
    ImageView picture;
    public Profile(Chat chat) {
        VBox vBox = new VBox(8);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 200,250);
        setScene(scene);
        picture = new ImageView(new Image(new ByteInputStream(chat.getPicture(), chat.getPicture().length)));
        picture.setFitWidth(150);
        picture.setFitHeight(150);
        picture.prefHeight(150);
        picture.prefWidth(150);
        name = new Label(chat.getName());
        lastname = new Label(chat.getLastname());
        username = new Label("@"+chat.getUsername());
        vBox.getChildren().addAll(picture,name,lastname,username);

    }

}
