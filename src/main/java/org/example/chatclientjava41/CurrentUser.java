package org.example.chatclientjava41;

import javax.swing.*;
import java.util.LinkedHashMap;

public class CurrentUser {
    private String role;
    private String birthdate;
    private String email;
    private String username;
    private String firstname;
    private String lastname;

    private ImageIcon avatar;
    private LinkedHashMap<String,String> chats = new LinkedHashMap<>();
    private String status;
    private String phoneNumber;

    public CurrentUser(String role, String birthdate, String email, String username, String firstname, String lastname) {
        this.role = role;
        this.birthdate = birthdate;
        this.email = email;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        System.out.println("Пользователь "+firstname+" "+lastname+" авторизовался в приложении");
    }
}
