package org.example.chatclientjava41;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RestoreMenuView{
    private RestoreMenuController restoreController;
    public Label error=new Label("");
    public RestoreMenuView(RestoreMenuController restoreController) {
        this.restoreController = restoreController;
    }
    public Scene RestoreScene(){
        TextField email=new TextField("Email");
        Button restoreButton=new Button();
        Button authMenuButton=new Button();
        restoreButton.setOnAction(actionEvent ->restoreController.clickRestore());
        authMenuButton.setOnAction(actionEvent ->restoreController.clickMenuAuth());
        HBox hBox=new HBox(restoreButton,authMenuButton);
        VBox vBox=new VBox(email, hBox,error);
        Scene scene = new Scene(vBox,200,200);
        return scene;
    }
}
