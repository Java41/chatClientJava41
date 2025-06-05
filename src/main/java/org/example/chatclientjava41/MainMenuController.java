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
    public static void sendMessageField(String textMessage){
        System.out.println(AllResponse.SendMessage(1,textMessage));
    }

    public VBox CurrentChat(List<MessageDTO> messages){
        VBox chat=new VBox();
        long id=ApplicationState.getApplicationState().getId();
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
        ArrayList<Contact> contacts=ApplicationState.getApplicationState().getContacts();
        if(contacts!=null){
            for (int i = 0; i<contacts.size(); i++) {
                Contact a1=contacts.get(i);
                HBox contactItem = new HBox();
                contactItem.setAlignment(Pos.CENTER_LEFT);
                contactItem.setSpacing(10);
                contactItem.setPadding(new Insets(5));
                contactItem.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc;");
                // Аватарка
                Circle avatarCircle = new Circle(20, Color.LIGHTGRAY);
                Label initialsLabel = new Label(a1.getFirstname().indexOf(0)+"");
                initialsLabel.setFont(Font.font(14));
                StackPane avatarStack = new StackPane();
                avatarStack.getChildren().addAll(avatarCircle, initialsLabel);
                // Имя и последнее сообщение
                VBox contactInfo = new VBox();
                contactInfo.setSpacing(2);

                Label nameLabel = new Label(a1.getFirstname()+" " + a1.getLastname());
                nameLabel.setFont(Font.font(14));

                Label lastMsgLabel = new Label("Последнее сообщение...");
                lastMsgLabel.setFont(Font.font(12));
                lastMsgLabel.setTextFill(Color.GRAY);

                contactInfo.getChildren().addAll(nameLabel, lastMsgLabel);
                contactItem.getChildren().addAll(avatarStack, contactInfo);
                ButtonChat contact=new ButtonChat("",contactItem);//кнопка будет содержать в себе чат
                contact.prefWidthProperty().bind(contactsList.widthProperty());
                contactsList.getChildren().add(contact);
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
