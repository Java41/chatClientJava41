package org.example.chatclientjava41;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

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
    public VBox getListChats(){
        VBox contactList=new VBox();
        for (int i = 1; i <= 5; i++) {

            HBox contactItem = new HBox();
            contactItem.setAlignment(Pos.CENTER_LEFT);
            contactItem.setSpacing(10);
            contactItem.setPadding(new Insets(5));
            contactItem.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc;");

            // Аватарка
            Circle avatarCircle = new Circle(20, Color.LIGHTGRAY);
            Label initialsLabel = new Label("A");
            initialsLabel.setFont(Font.font(14));
            StackPane avatarStack = new StackPane();
            avatarStack.getChildren().addAll(avatarCircle, initialsLabel);

            // Имя и последнее сообщение
            VBox contactInfo = new VBox();
            contactInfo.setSpacing(2);

            Label nameLabel = new Label("Контакт " + i);
            nameLabel.setFont(Font.font(14));

            Label lastMsgLabel = new Label("Последнее сообщение...");
            lastMsgLabel.setFont(Font.font(12));
            lastMsgLabel.setTextFill(Color.GRAY);

            contactInfo.getChildren().addAll(nameLabel, lastMsgLabel);
            contactItem.getChildren().addAll(avatarStack, contactInfo);
            ButtonChat contact=new ButtonChat("",contactItem);//кнопка будет содержать в себе чат
//            contact.prefWidthProperty().bind(contactsList.widthProperty());
//            contactsList.getChildren().add(contact);

        }
        return contactList;
    }
    public void getProfileMainUser(){//добавить окно
        ProfileElementCreator.showProfileWindow(ApplicationState.getApplicationState());
    }
}
