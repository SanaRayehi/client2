package com.example.server;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.File;
import java.net.URL;

public class Setting extends Stage {
    public Setting(User user, Stage parent) {
        VBox vBox = new VBox(8);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 200,300);
        ImageView imageView = new ImageView(new Image(new ByteInputStream(user.getProfilePicture(), user.getProfilePicture().length)));
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        imageView.prefWidth(150);
        imageView.prefHeight(150);
        TextField nameTextField = new TextField();
        nameTextField.setText(user.getName());
        TextField lastnameTextField = new TextField();
        lastnameTextField.setText(user.getLastname());
        Button deleteAccountButton = new Button("Delete Account");
        Button logoutButton = new Button("Logout");


        vBox.getChildren().addAll( imageView, nameTextField,lastnameTextField, deleteAccountButton , logoutButton);
        setScene(scene);
        vBox.requestFocus();

        //action
        imageView.setOnMouseClicked(event -> {
            if(event.getClickCount()==2) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("choose your pic", "*.png"));
                File file = fileChooser.showOpenDialog(Setting.this);
                String path =file.getAbsolutePath();
                imageView.setImage(new Image("file:///"+path));
                byte[] data = FileUtilty.getImageData(path);
                user.setProfilePicture(data);
            }
        });

        setOnCloseRequest(event -> {
            user.setName(nameTextField.getText());
            user.setLastname(lastnameTextField.getText());
            UpdateUserDto updateUserDto = new UpdateUserDto(user.getUsername(),user.getName(), user.getLastname(),user.getProfilePicture());
            ClientSide.updateUser(updateUserDto);
        });

        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ClientSide.logout(user.getUsername());
                Setting.this.close();
                parent.close();
                Login login = new Login();
                login.show();
            }
        });

        deleteAccountButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ClientSide.deleteUser(user.getUsername());
                Setting.this.close();
                parent.close();
                Login login = new Login();
                login.show();
            }
        });


    }

}
