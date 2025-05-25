package org.example.chatclientjava41;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RegistrationMenuView {
    private RegistrationMenuController registrationController;
    private Label error = new Label(" ");
    public RegistrationMenuView(RegistrationMenuController registrationController) {
        this.registrationController = registrationController;
    }
    public Scene RegistrationScene() {

        TextField login = new TextField("Логин:");
        TextField password = new TextField("Пароль:");
        TextField email = new TextField("Email:");
        Button authMenuButton = new Button();
        Button registrationButton = new Button();
        registrationButton.setOnAction(actionEvent -> registrationController.clickRegistration(login.getText(),password.getText(), email.getText()));
        authMenuButton.setOnAction(actionEvent -> registrationController.clickMenuAuth());
        HBox hBox1 = new HBox(registrationButton,authMenuButton);
        VBox vBox = new VBox(login,email,password,hBox1,error);
        Scene scene = new Scene(vBox, 200, 300);
        return scene;
    }
    public void setError(String error) {
        this.error.setText(error);
    }
}

