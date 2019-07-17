package com.example.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class ClientSide {
    public static ServiceHandler serviceHandler;
    public static void register(SignUpDto signUpDto) {
        serviceHandler.register(signUpDto);
    }
    public static boolean activeUser(String username, int code) {
       return serviceHandler.activeUser(username, code);
    }

    public static UserBaseInfoDto login(String username, String password) throws UserNotFoundException {
        return serviceHandler.login(username, password);
    }
    public static void updateUser(UpdateUserDto updateUserDto) {
        serviceHandler.update(updateUserDto);
    }
    public static List<Message> getHistory(int chatId) {
        return serviceHandler.getHistory(chatId);
    }

    public static Chat findUser(String username, String peer) throws UserNotFoundException {
        return serviceHandler.findUser(username, peer);
    }
    public static void logout(String username) {
        serviceHandler.logout(username);
    }
    public static Socket getFileSocket(String username, int chatid, String fileName) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress("localhost",12345));
            socket.getOutputStream().write((username+","+chatid+","+fileName).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }
    public static boolean checkUserActivity(String username) {
        return serviceHandler.userStatus(username);
    }
}
