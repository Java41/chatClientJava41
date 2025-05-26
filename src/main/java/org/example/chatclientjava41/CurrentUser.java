package org.example.chatclientjava41;

import javax.swing.*;
import java.util.LinkedHashMap;

public class CurrentUser {
    private String username;
    private String id;
    private ImageIcon avatar;
    private LinkedHashMap<String,String> chats = new LinkedHashMap<>();
    private String status;
    private String phoneNumber;

    public CurrentUser(String username, String id, ImageIcon avatar, LinkedHashMap<String, String> chats, String status, String phoneNumber) {
        this.username = username;
        this.id = id;
        this.avatar = avatar;
        this.chats = chats;
        this.status = status;
        this.phoneNumber = phoneNumber;
    }
}
