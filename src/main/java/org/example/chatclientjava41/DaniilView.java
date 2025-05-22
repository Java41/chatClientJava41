package org.example.chatclientjava41;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DaniilView {
    public Label error = new Label(" ");
    private final DaniilController daniilController;

    public DaniilView ( DaniilController daniilController ) {
        this.daniilController = daniilController;
    }

    public Scene DaniilView () {
        TextField login = new TextField("Логин: ");
        Label nickname = new Label(" ");
        TextField password = new TextField("Пароль: ");
        Label you_password = new Label(" ");
        Button Login = new Button();
        Button Registration = new Button();
        Button Recovery_password = new Button();
        Button restorePassword = new Button();
        Login.setOnAction(actionEvent -> daniilController.clickEnter(login.getText(), password.getText());
        Registration.setOnAction(actionEvent -> daniilController.clickMenuRestorePass());
        restorePassword.setOnAction(actionEvent -> daniilController.clickMenuRegistration());
        HBox hbox1 = new HBox(nickname, login);
        HBox hbox2 = new HBox(you_password, password);
        VBox vbox = new VBox(hbox1, hbox2, Login, Registration, Recovery_password);
        Scene scene1 = new Scene(vbox, 200, 300);
        return scene1;
    }
}
