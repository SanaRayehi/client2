package com.example.server;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Optional;

public class SignUp extends Stage {
    ClientSide clientSide;
    private TextField nameTextField;
    private TextField lastnameTextField;
    private TextField emailTextField;
    private TextField usernameTextField;
    private PasswordField passwordField;
    private PasswordField passwordField2;
    private String picture;
    private ImageView pictureView;
    public SignUp() {
        VBox vBox = new VBox(8);
        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox, 250,500);
        URL imagePath = ClassLoader.getSystemResource("register-logo.png");
        URL imagePath2 = ClassLoader.getSystemResource("addpic-logo.png");
        ImageView imageView = new ImageView(new Image(imagePath.toExternalForm()));
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        imageView.prefWidth(80);
        imageView.prefHeight(80);
        nameTextField = new TextField();
        lastnameTextField = new TextField();
        emailTextField = new TextField();
        usernameTextField = new TextField();
        passwordField = new PasswordField();
        passwordField2 = new PasswordField();
        pictureView = new ImageView();
        pictureView = new ImageView(new Image(imagePath2.toExternalForm()));
        nameTextField.setPromptText("enter name");
        lastnameTextField.setPromptText("enter lastname");
        emailTextField.setPromptText("enter email");
        usernameTextField.setPromptText("enter username");
        passwordField.setPromptText("enter password");
        passwordField2.setPromptText("enter password again");
        pictureView.setFitWidth(80);
        pictureView.setFitHeight(80);
        pictureView.prefWidth(80);
        pictureView.prefHeight(80);
        Button button = new Button("Choose Picture");
        Button registerButton = new Button("Register");
        registerButton.setDisable(true);
        Hyperlink hyperlink = new Hyperlink("back");
        vBox.getChildren().addAll( imageView, nameTextField, lastnameTextField, emailTextField,usernameTextField,passwordField,passwordField2,pictureView,button,registerButton, hyperlink);
        setScene(scene);
        vBox.requestFocus();

        //action
        hyperlink.setOnAction(event -> back());

        button.setOnAction(event -> {
            registerButton.setDisable(false);
            FileChooser  fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("choose your pic", "*.png"));
            File file = fileChooser.showOpenDialog(SignUp.this);
            picture =file.getAbsolutePath();
            pictureView.setImage(new Image("file:///"+file.getPath()));

        });

        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!passwordField.getText().equals(passwordField2.getText()) || passwordField.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "passwords does not match");
                    alert.showAndWait();
                    return;
                }
                SignUpDto signUpDto = new SignUpDto(nameTextField.getText(), lastnameTextField.getText(),
                        emailTextField.getText(), usernameTextField.getText(),passwordField.getText(),FileUtilty.getImageData(picture));
                clientSide.register(signUpDto);
                TextInputDialog textInputDialog = new TextInputDialog();
                textInputDialog.setHeaderText("Enter Your activation code");
                textInputDialog.setTitle("");
                Optional<String> result = textInputDialog.showAndWait();
                if(result.isPresent()) {
                    boolean actived = ClientSide.activeUser(signUpDto.getUsername(), Integer.parseInt(result.get()));
                    if(actived) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You Registered successfully");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Activation code is wrong");
                        alert.showAndWait();
                    }
                    close();
                }
            }
        });
    }
    public void back(){
        close();
    }


}
