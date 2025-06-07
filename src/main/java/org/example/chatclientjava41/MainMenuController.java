package org.example.chatclientjava41;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.chatclientjava41.dto.MessageDTO;
import org.example.chatclientjava41.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class MainMenuController {
    private MainMenuView view;
    private SceneNavigator sceneNavigator;

    public void setView(MainMenuView view){
        this.view=view;
    }
    public void setSceneNavigator(SceneNavigator sceneNavigator){
        this.sceneNavigator=sceneNavigator;
    }
    public static void sendMessageField(String textMessage,Long id){
        System.out.println(AllResponse.SendMessage(id,textMessage));
    }

    public void CurrentChat(Contact contact){
        long id=ApplicationState.getApplicationState().getId();//наш id
        ArrayList<MessageDTO> messages=contact.getMessages();
        UserDTO currentInterlocutor=contact.getUserDTO();
        //____________шапка чата________________
        Label nameLbl= new Label(currentInterlocutor.username());
        Label timeLbl= new Label("Последняя активность: 12:45");
        Button callBtn= new Button("Звонок");
        Button videoCallBtn= new Button("Видео звонок");
        HBox header=new HBox(new VBox(nameLbl,timeLbl),new HBox(callBtn,videoCallBtn));
        //____________поле чата___________________
        VBox messagesVbox=new VBox();
        if(messages!=null){
            for (MessageDTO messageDTO : messages) {
                HBox bubbleHBox= new HBox();
                Label messageLbl= new Label(messageDTO.content());
                messageLbl.borderProperty().set(new Border(new BorderStroke(Color.GRAY,BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(1)))
                );
                if(messageDTO.senderId()==id){
                    bubbleHBox.setAlignment(Pos.CENTER_RIGHT);
                    messageLbl.backgroundProperty().set(new Background(new BackgroundFill(Color.LIGHTGREEN,new CornerRadii(5),Insets.EMPTY)));
                    bubbleHBox.getChildren().addAll(new Region(),messageLbl);
                } else{
                    bubbleHBox.setAlignment(Pos.CENTER_LEFT);
                    messageLbl.backgroundProperty().set(new Background(new BackgroundFill(Color.WHITE,new CornerRadii(5),Insets.EMPTY)));
                    bubbleHBox.getChildren().addAll(messageLbl , new Region());
                }
                messagesVbox.getChildren().add(bubbleHBox);
            }
        }
        //________________низ чата______________________________
        TextField messageInput= new TextField();
        messageInput.setOnAction(event ->sendMessageField(messageInput.getText(),currentInterlocutor.id()));
        messageInput.setPromptText("Введите сообщение");
        Button voiceMsgBtn= new Button("\uD83D\uDD0A"); // Микрофон или голосовое сообщение
        Button emojiBtn= new Button("\uD83D\uDE03");     // Эмодзи
        Button imageBtn= new Button("\uD83D\uDCF7");     // Изображение
        HBox messageInputArea= new HBox(messageInput,voiceMsgBtn,emojiBtn,imageBtn);
        VBox chat=new VBox(header,messagesVbox,messageInputArea);
        view.setMessagesContainer(chat);
    }
    public void ContactList(){
        VBox contactsList=new VBox();
        List<Contact> contacts=ApplicationState.getApplicationState().getContacts();
        if(contacts!=null){
            for (int i = 0; i<contacts.size(); i++) {
                HBox contactItem = new HBox();
                Contact interlocutor=contacts.get(i);
                Circle avatarCircle = new Circle(20, Color.LIGHTGRAY);
                Label initialsLabel = new Label(interlocutor.getUserDTO().firstName());
                StackPane avatarStack = new StackPane(avatarCircle, initialsLabel);// Аватарка
                Label nameLabel = new Label(interlocutor.getUserDTO().firstName()+" " + interlocutor.getUserDTO().lastName());
                Label lastMsgLabel = new Label("Последнее сообщение...");
                contactItem.getChildren().addAll(avatarStack, new VBox(nameLabel, lastMsgLabel));
                contactItem.setOnMousePressed(e -> {
                    contactItem.setStyle("-fx-background-color: #cccccc;"); // Эффект нажатия
                    CurrentChat(interlocutor);
                });
                contactsList.getChildren().add(contactItem);
            }
        }
        view.setContactsList(contactsList);
    }
    public void Logout(){
        AllResponse.Logout();
    }

    public void CreateContact(long id){
        AllResponse.AddContact(id);
    }
    public void getProfileMainUser(){//добавить окно
        ProfileElementCreator.showProfileWindow(ApplicationState.getApplicationState());
    }
}
