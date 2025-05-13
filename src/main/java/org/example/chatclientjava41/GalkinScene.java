package org.example.chatclientjava41;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GalkinScene {
    public Scene GalkinDaniil(){
        VBox vBox=new VBox();
        Scene scene = new Scene(vBox,200,200);
        Label label=new Label("Галкин Даниил приветствует вас");
        vBox.getChildren().add(label);
        return scene;
    }
}
