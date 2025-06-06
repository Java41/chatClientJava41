package org.example.chatclientjava41;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
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

    public VBox CurrentChat(List<MessageDTO> messages){
        VBox chat=new VBox();
        long id=ApplicationState.getApplicationState().getId();//наш id
        for (MessageDTO messageDTO : messages) {
            HBox bubbleHBox= new HBox();
            if(messageDTO.senderId()==id){
                bubbleHBox.setAlignment(Pos.CENTER_RIGHT);
            } else{
                bubbleHBox.setAlignment(Pos.CENTER_LEFT);
            }

            Label messageLbl= new Label(messageDTO.content());
            messageLbl.wrapTextProperty().set(true);
            messageLbl.maxWidthProperty().setValue(300);
            messageLbl.paddingProperty().setValue(new Insets(5));

            BackgroundFill fillColor= (messageDTO.senderId()==id) ?
                    new BackgroundFill(Color.LIGHTGREEN,new CornerRadii(5),Insets.EMPTY):
                    new BackgroundFill(Color.WHITE,new CornerRadii(5),Insets.EMPTY);

            messageLbl.backgroundProperty().set(new Background(fillColor));

            messageLbl.borderProperty().set(
                    new Border(new BorderStroke(Color.GRAY,
                            BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(1)))
            );
            if(messageDTO.senderId()==id){
                bubbleHBox.getChildren().addAll(spacer(),messageLbl);
                bubbleHBox.alignmentProperty().set(Pos.CENTER_RIGHT);
            }
            else{
                bubbleHBox.getChildren().addAll(messageLbl , spacer());
                bubbleHBox.alignmentProperty().set(Pos.CENTER_LEFT);
            }
        }
        return chat;

    }
    private Region spacer() {
        Region spacer=new Region();
        HBox.setHgrow(spacer , Priority.ALWAYS );
        return spacer;
    }

    public VBox ContactList(){
        VBox contactsList=new VBox();
        List<Contact> contacts=ApplicationState.getApplicationState().getContacts();
        if(contacts!=null){
            for (int i = 0; i<contacts.size(); i++) {
                HBox contactItem = new HBox();
                UserDTO recipientUser=contacts.get(i).getUserDTO();
                // Аватарка
                Circle avatarCircle = new Circle(20, Color.LIGHTGRAY);
                Label initialsLabel = new Label(recipientUser.firstName());
                StackPane avatarStack = new StackPane();
                avatarStack.getChildren().addAll(avatarCircle, initialsLabel);
                // Имя и последнее сообщение
                VBox contactInfo = new VBox();
                Label nameLabel = new Label(recipientUser.firstName()+" " + recipientUser.lastName());
                Label lastMsgLabel = new Label("Последнее сообщение...");
                contactInfo.getChildren().addAll(nameLabel, lastMsgLabel);

                contactItem.getChildren().addAll(avatarStack, contactInfo);
                contactItem.setOnMouseClicked(actionEvent ->CurrentChat(contacts.get(0).getMessages()));//вытащить id ИЗ КОНТАКТОВ
                contactItem.prefWidthProperty().bind(contactsList.widthProperty());
                contactsList.getChildren().add(ContactList());
            }
        }
        return contactsList;
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
