package org.example.chatclientjava41;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AuthorizationMenuView {
    public Label error = new Label("");
    private final AuthorizationMenuController daniilController;

    public AuthorizationMenuView ( AuthorizationMenuController daniilController ) {
        this.daniilController = daniilController;
    }

    public Scene AuthorizationScene () {
        TextField login = new TextField("Логин:");
        TextField password = new TextField("Пароль:");
        Button logInButton = new Button();
        Button registrationMenuButton = new Button();
        Button restoreMenuButton = new Button();
        logInButton.setOnAction(actionEvent -> daniilController.clickEnter(login.getText(), password.getText()));
        registrationMenuButton.setOnAction(actionEvent -> daniilController.clickMenuRegistration());
        restoreMenuButton.setOnAction(actionEvent -> daniilController.clickMenuRestorePass());
        HBox hbox1 = new HBox(logInButton,registrationMenuButton,restoreMenuButton);
        VBox vbox = new VBox(login,password,hbox1,error);
        Scene scene = new Scene(vbox, 200, 300);
        return scene;
    }
}
