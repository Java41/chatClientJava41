package org.example.chatclientjava41;

import org.example.chatclientjava41.dto.MessageDTO;
import org.example.chatclientjava41.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private UserDTO contact;
    private ArrayList<MessageDTO> messages=new ArrayList<>();

    private Contact(UserDTO contact) {
        this.contact=contact;
        this.messages.addAll(AllResponse.GetMessage(contact.id()));
    }
    public UserDTO getUserDTO() {
        return contact;
    }

    public ArrayList<MessageDTO> getMessages() {
        return messages;
    }
}
