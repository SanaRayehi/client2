package com.example.server;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatRoom extends Stage {
    List<Thread> uploadThreads;
    TextField messageInput;
    private Socket socket;
    private boolean sentTypingStatus;
    private VBox pane;
    private Label label;
    protected ScrollPane scrollPane;
    public ChatRoom(Chat chat, String senderUsername) {
        pane = new VBox();
        pane.getStylesheets().add(0, ClassLoader.getSystemResource("style.css").toExternalForm());
        uploadThreads = new ArrayList<>();
        VBox vBox = new VBox(8);
        scrollPane = new ScrollPane();
        UserStatusDto statusDto = ClientSide.checkUserActivity(chat.getUsername());
        if(statusDto.isStatus()) {
            label = new Label(chat.getUsername()+" (online)");
        }else {
            label = new Label(chat.getUsername()+" (lastseen "+statusDto.getDate()+")");
        }
        label.setTextAlignment(TextAlignment.CENTER);
        label.setStyle("-fx-background-color: aqua");
        pane.setAlignment(Pos.CENTER);
        scrollPane.setContent(pane);
        vBox.setAlignment(Pos.TOP_CENTER);
        messageInput = new TextField();
        messageInput.setId("messageInput");
        Button chooseFile = new Button("File");
        Button emoji = new Button("Emoji");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(chooseFile,emoji);
        vBox.getChildren().addAll(label,scrollPane, messageInput,hBox);
        scrollPane.prefHeightProperty().bind(heightProperty().subtract(60));
        Scene scene = new Scene(vBox, 400,400);
        setScene(scene);
        label.setOnMouseClicked(event -> {
            if(event  .getClickCount()==2) {
                Profile profile = new Profile(chat);
                profile.show();
            }
        });

        emoji.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EmojiPanel emojiPanel = new EmojiPanel(messageInput);
                emojiPanel.show();
            }
        });

        chooseFile.setOnAction(new EventHandler<ActionEvent>() {
                                   @Override
                                   public void handle(ActionEvent event) {
                                       FileChooser fileChooser = new FileChooser();
                                       File file = fileChooser.showOpenDialog(ChatRoom.this);
                                       try {
                                           ReceiverFileTemplate receiverFileTemplate = new ReceiverFileTemplate(file.getName(), new Date(),file.getAbsolutePath());
                                           receiverFileTemplate.setProgressBarValue(0);
                                           pane.getChildren().add(receiverFileTemplate);
                                           Thread thread = new Thread(new UploaderThread(socket.getOutputStream(), file.getAbsolutePath(), file.getName(), receiverFileTemplate.progressBar)){
                                               @Override
                                               public synchronized void start() {
                                                   for(Thread th: uploadThreads) {
                                                       try {
                                                           if(th.isAlive())
                                                            th.join();
                                                       } catch (InterruptedException e) {
                                                           e.printStackTrace();
                                                       }
                                                   }
                                                   uploadThreads.add(this);
                                                   super.start();
                                               }
                                           };
                                           thread.start();
                                       } catch (IOException e) {
                                           e.printStackTrace();
                                       }
                                   }});
        getHistory(senderUsername, chat.getId());
        startConnection(senderUsername,chat.getId());
        scrollPane.setVvalue(1.0);
        scrollPane.vvalueProperty().bind(pane.heightProperty());

        messageInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.isEmpty()){
                    try {
                        socket.getOutputStream().write("stat:online".getBytes());
                        sentTypingStatus = false;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        if(!sentTypingStatus) {
                            socket.getOutputStream().write("stat:typing...".getBytes());
                            sentTypingStatus = true;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        messageInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    String message = messageInput.getText();
                    try {
//                        message = MessageCipher.encryptMessage(message);
                        socket.getOutputStream().write(("text:"+message).getBytes());
                        messageInput.clear();
                        pane.getChildren().add(new ReceiverMessageTemplate(message, new Date()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getHistory(String senderUsername, int id) {
        List<Message> messageList = ClientSide.getHistory(id);
        for(Message message: messageList) {
            if(message.getUsername().equals(senderUsername)) {
                if(message.getMessage()!=null) {
                    ReceiverMessageTemplate receiverMessageTemplate = new ReceiverMessageTemplate(message.getMessage(), message.getDate());
                    pane.getChildren().add(receiverMessageTemplate);
                }else{
                    ReceiverFileTemplate receiverFileTemplate = new ReceiverFileTemplate(message.getFileNAme(), message.getDate(),null);
                    receiverFileTemplate.progressBar.setProgress(1);
                    pane.getChildren().add(receiverFileTemplate);
                }
            }else {
                if(message.getMessage()!=null) {
                    SenderMessageTemplate senderMessageTemplate = new SenderMessageTemplate(message.getMessage(), message.getDate());
                    senderMessageTemplate.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
                    pane.getChildren().add(senderMessageTemplate);
                } else{
                    SenderFileTemplate senderFileTemplate = new SenderFileTemplate(message.getFileNAme(), message.getDate(),null, senderUsername, id);
                    senderFileTemplate.progressBar.setProgress(1);
                    senderFileTemplate.prefWidthProperty().bind(scrollPane.widthProperty().subtract(20));
                    pane.getChildren().add(senderFileTemplate);
                }
            }
        }
    }

    private void startConnection(String username, int chatid) {
        socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("localhost",12345));
            System.out.println(username+","+chatid);
            socket.getOutputStream().write((username+","+chatid).getBytes());
            ListenerService listenerService = new ListenerService(socket.getInputStream(), pane, scrollPane, username, chatid, label);
            Thread thread = new Thread(listenerService);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
