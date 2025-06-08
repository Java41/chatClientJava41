package org.example.chatclientjava41;

import org.example.chatclientjava41.dto.MessageDTO;
import org.example.chatclientjava41.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;
interface ObservMessage{
    void updateMessage();
}

public class Contact implements ObservMessage{
    private UserDTO contact;
    private ArrayList<MessageDTO> messages=new ArrayList<>();

    public Contact(UserDTO contact) {
        this.contact=contact;
    }
    public UserDTO getUserDTO() {
        return contact;
    }

    public ArrayList<MessageDTO> getMessages() {
        return messages;
    }

    @Override
    public void updateMessage() {
       messages.addAll(AllResponse.GetMessage(contact.id()));//должны приходить сообщения новые, а приходят все
    }
}
