package org.example.chatclientjava41;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
        long id=ApplicationState.getApplicationState().getId();
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
                    bubbleHBox.getChildren().addAll(messageLbl, new Region());
                }
                messagesVbox.getChildren().add(bubbleHBox);
            }
        }
        ScrollPane scrollPane=new ScrollPane();
        scrollPane.setContent(messagesVbox);
        //________________низ чата______________________________
        TextField messageInput= new TextField();
        messageInput.setOnAction(event ->sendMessageField(messageInput.getText(),currentInterlocutor.id()));
        messageInput.setPromptText("Введите сообщение");
        Button voiceMsgBtn= new Button("\uD83D\uDD0A"); // Микрофон или голосовое сообщение
        Button emojiBtn= new Button("\uD83D\uDE03");     // Эмодзи
        Button imageBtn= new Button("\uD83D\uDCF7");     // Изображение
        HBox messageInputArea= new HBox(messageInput,voiceMsgBtn,emojiBtn,imageBtn);
        VBox chat=new VBox(header,scrollPane,messageInputArea);
        view.setMessagesContainer(chat);
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
