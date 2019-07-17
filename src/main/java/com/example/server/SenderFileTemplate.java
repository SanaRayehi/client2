package com.example.server;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class SenderFileTemplate extends FileTemplate {
    long fileSize = 0;
    double receivedSize = 0;
    public SenderFileTemplate(String text, Date date, String path, String username, int chatid) {
        super(text, date, path);
        setAlignment(Pos.CENTER_RIGHT);
        message.setAlignment(Pos.CENTER_RIGHT);
        message.setTextAlignment(TextAlignment.RIGHT);
        message.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(SenderFileTemplate.this.path != null) {
                    try {
                        Runtime.getRuntime().exec("explorer.exe /select," + SenderFileTemplate.this.path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setInitialFileName(message.getText());
                    File file= fileChooser.showSaveDialog(null);
                    new Thread(new Task() {
                        @Override
                        protected Object call() throws Exception {
                            int size = 0;

                            FileOutputStream fileOutputStream = null;
                            Socket socket = ClientSide.getFileSocket(username,chatid,message.getText());
                            byte[] buffer = new byte[8192];
                            try {
                                while (true) {
                                    size = socket.getInputStream().read(buffer);
                                    if (size == -1) {
                                        fileOutputStream.close();
                                        SenderFileTemplate.this.path = file.getAbsolutePath();
                                        System.out.println(file.getAbsolutePath());
                                        return null;
                                    }
                                    String sizeInfo = new String(buffer, 0, 4);
                                    if (sizeInfo.equals("size")) {
                                        String text = new String(buffer, 0, size);
                                        int indexOfColon = text.indexOf(":", 5);
                                        fileSize = Integer.parseInt(text.substring(5, indexOfColon));
                                        fileOutputStream = new FileOutputStream(file.getAbsolutePath());
                                        fileOutputStream.write(buffer,indexOfColon+1,size-indexOfColon-1);
                                        receivedSize+= size-indexOfColon-1;
                                    } else {
                                        fileOutputStream.write(buffer, 0 , size);
                                        receivedSize+=size;
                                    }
                                    Platform.runLater(new Runnable() {
                                      @Override
                                      public void run() {
                                            progressBar.progressProperty().setValue(receivedSize/fileSize);
                                       }
                                    });



                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }).start();

                }
            }
        });
    }
}
