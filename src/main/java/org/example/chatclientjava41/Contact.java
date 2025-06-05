package org.example.chatclientjava41;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.chatclientjava41.dto.MessageDTO;
import org.example.chatclientjava41.dto.UserDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Contact {
    private UserDTO contact;
    private List<MessageDTO> messages=new ArrayList<>();

    private Contact(UserDTO contact) {
        this.contact=contact;
        this.messages.addAll(AllResponse.GetMessage(contact.id()));
    }
    public Long getId() {
        return contact.id();
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }
}
