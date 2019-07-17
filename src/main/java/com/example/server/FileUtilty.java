package com.example.server;

import javafx.scene.image.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtilty {

    public static byte[] getImageData(String imageAddress) {
        try {
            return Files.readAllBytes(Paths.get(imageAddress));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
