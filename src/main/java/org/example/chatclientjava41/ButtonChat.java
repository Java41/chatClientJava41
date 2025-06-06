package org.example.chatclientjava41;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import org.example.chatclientjava41.dto.UserDTO;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ButtonChat extends Button {
    private UserDTO userDTO;
    private VBox buttonUI;

    public ButtonChat(UserDTO userDTO) {
        HBox contactItem = new HBox();
        contactItem.setAlignment(Pos.CENTER_LEFT);
        contactItem.setSpacing(10);
        contactItem.setPadding(new Insets(5));
        contactItem.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc;");
        // Аватарка
        Circle avatarCircle = new Circle(20, Color.LIGHTGRAY);
        Label initialsLabel = new Label(userDTO.firstName());
        initialsLabel.setFont(Font.font(14));
        StackPane avatarStack = new StackPane();
        avatarStack.getChildren().addAll(avatarCircle, initialsLabel);
        // Имя и последнее сообщение
        VBox contactInfo = new VBox();
        contactInfo.setSpacing(2);

        Label nameLabel = new Label(userDTO.firstName()+" " + userDTO.lastName());
        nameLabel.setFont(Font.font(14));

        Label lastMsgLabel = new Label("Последнее сообщение...");
        lastMsgLabel.setFont(Font.font(12));
        lastMsgLabel.setTextFill(Color.GRAY);

        contactInfo.getChildren().addAll(nameLabel, lastMsgLabel);
        contactItem.getChildren().addAll(avatarStack, contactInfo);

    }
}
