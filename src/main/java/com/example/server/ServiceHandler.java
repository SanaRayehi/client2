package com.example.server;

import java.util.List;

public interface ServiceHandler {
    void register(SignUpDto signUpDto);
    boolean activeUser(String username, int activecode);
    UserBaseInfoDto login(String username, String password) throws UserNotFoundException;
    void update(UpdateUserDto updateUserDto);
    List<Message> getHistory(int chatid);
    Chat findUser(String username, String peer) throws UserNotFoundException;
    void logout(String username);
    UserStatusDto userStatus(String username);
    void deleteUser(String username);

}
