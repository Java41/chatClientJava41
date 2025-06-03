package org.example.chatclientjava41;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class Contact {
    private String id;
    private String username;
    private String firstname;
    private String lastname;
    private String photoUrl;

    private Contact(String id, String username, String firstname, String lastname, String photoUrl) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.photoUrl = photoUrl;
    }

    public static void creatContact(String responseBody) {
        ArrayList<Contact>contacts=new ArrayList<>();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            for (int i=0;i<objectMapper.mixInCount();i++){
                JsonNode jsonNode = objectMapper.readTree(responseBody).get(i);
                Contact a1=new Contact(jsonNode.get("id").asText(),jsonNode.get("username").asText(),jsonNode.get("firstname").asText(),jsonNode.get("lastname").asText(),jsonNode.get("photoUrl").asText());
                contacts.add(a1);
            }
            ApplicationState.getApplicationState().setContacts(contacts);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}
