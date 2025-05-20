package org.example.chatclientjava41;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ViktorController {

    private SceneNavigator sceneNavigator;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    public void setSceneNavigator(SceneNavigator sceneNavigator) {
        this.sceneNavigator = sceneNavigator;
    }

    @FXML
    private void clickMenuAuth(){
        sceneNavigator.setAuth();
    }

    @FXML
    private void clickRegistration(){
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login.isEmpty()){
            errorLabel.setText("Логин не может быть пустым");
            return;
        }
        if (password == null || password.isEmpty()){
            errorLabel.setText("Пароль не может быть пустым");
            return;
        }
        errorLabel.setText("Регистрация прошла успешно");
    }

}
