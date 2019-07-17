package com.example.server;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

import java.io.*;

public class UploaderThread extends Task {
    ProgressBar progressBar;
    OutputStream outputStream;
    String path;
    String name;
    public UploaderThread(OutputStream outputStream, String path, String name, ProgressBar progressBar) {
        this.outputStream = outputStream;
        this.path = path;
        this.name = name;
        this.progressBar = progressBar;
    }

    @Override
    protected Object call() throws Exception {
        byte[] buffer = new byte[20];
        FileInputStream fileInputStream = new FileInputStream(path);
        long totalSize = new File(path).length();
        double sendSize = 0;
        int size = 0;
        outputStream.write(("file:"+name).getBytes());
        while ((size= fileInputStream.read(buffer))!=-1) {
            outputStream.write(buffer,0,size);
            sendSize+=size;
            double progress = sendSize/totalSize;
            progressBar.progressProperty().setValue(progress);
        }
        outputStream.write("end".getBytes());
        return null;
    }
}
