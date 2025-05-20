package org.example.chatclientjava41;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerView {
    private final Stage stage;

    public ServerView(Stage stage) {
        this.stage = stage;
    }

    public void setAuth(){
        //loadScene("", "");
    }

    public void setRegistration(){
        //loadScene("", "");
    }

    private void loadScene(String fxmlPath, String title){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
