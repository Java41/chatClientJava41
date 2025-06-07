package org.example.chatclientjava41;

import org.example.chatclientjava41.dto.MessageDTO;
import org.example.chatclientjava41.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private UserDTO contact;
    private ArrayList<MessageDTO> messages=new ArrayList<>();

    public Contact(UserDTO contact) {
        this.contact=contact;
        System.out.println(contact.id()+"Добавлен в контакты");
        this.messages.addAll(AllResponse.GetMessage(contact.id()));
        if(messages!=null){
            for (MessageDTO i:messages){
                System.out.println(i.content()+"/n");
            }
        }
    }
    public UserDTO getUserDTO() {
        return contact;
    }

    public ArrayList<MessageDTO> getMessages() {
        return messages;
    }
}
