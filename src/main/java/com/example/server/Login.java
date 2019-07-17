package com.example.server;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;

public class Login extends Stage {
    public Login() {
        VBox vBox = new VBox(8);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 200,200);
        URL imagePath = ClassLoader.getSystemResource("chat-logo.png");
       ImageView imageView = new ImageView(new Image(imagePath.toExternalForm()));
        TextField userTextFiled = new TextField();
        userTextFiled.setPromptText("enter usrename");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("enter password");
        Hyperlink register = new Hyperlink("register");
        Button login = new Button("Login");
        vBox.getChildren().addAll( imageView, userTextFiled,passwordField,login,register);
        setScene(scene);
        vBox.requestFocus();


        register.setOnAction(event -> {
            SignUp signUp = new SignUp();
            signUp.show();
        });

        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = userTextFiled.getText();
                String password  = passwordField.getText();
                try {
                    UserBaseInfoDto userBaseInfoDto = ClientSide.login(username, password);
                    Login.this.close();
                    App app = new App(userBaseInfoDto);
                    app.show();
                } catch (UserNotFoundException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR , "Username or Password is not correct");
                    alert.show();
                }
            }
        });

    }

}
