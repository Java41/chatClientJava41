package org.example.chatclientjava41;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RestoreView{
    private RestoreController restoreController;
    public Label error;
    public RestoreView(RestoreController restoreController) {
        this.restoreController = restoreController;
    }
    public Scene RestoreScene(){
        TextField restore=new TextField();
        Label email=new Label("Email");
        Button buttonRestore=new Button();
        buttonRestore.setOnAction(actionEvent ->restoreController.clickRestore());
        Button buttonAuth=new Button();
        buttonAuth.setOnAction(actionEvent ->restoreController.clickMenuAuth());
        HBox hBox=new HBox(email,restore);
        VBox vBox=new VBox(hBox,buttonRestore,error);
        Scene scene = new Scene(vBox,200,200);
        return scene;
    }
}
