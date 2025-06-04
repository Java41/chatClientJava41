package org.example.chatclientjava41;

import javafx.geometry.Pos;
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

        VBox vBox = new VBox();
        Scene scene = new Scene(vBox,500, 250);
        scene.getStylesheets().add(getClass().getResource("/StyleRestoreMenu.css").toExternalForm());

        TextField login = new TextField();
        login.setPromptText("Логин:");
        TextField password = new TextField();
        password.setPromptText("Пароль:");
        TextField email = new TextField();
        email.setPromptText("Email:");
        TextField date = new TextField();
        date.setPromptText("Дата рождения");

        Button authMenuButton = new Button("Назад к авторизации");
        Button registrationButton = new Button("Зарегистрироваться");
        registrationButton.setOnAction(actionEvent -> registrationController.clickRegistration(login.getText(),password.getText(), email.getText(), date.getText()));
        //authMenuButton.getStyleClass().add("secondary");
        authMenuButton.setOnAction(actionEvent -> registrationController.clickMenuAuth());

        HBox hBox1 = new HBox(registrationButton,authMenuButton);

        vBox.getChildren().addAll(login, email, password, date, hBox1, error);
        vBox.setSpacing(10);//расстояние между объектами внутри
        vBox.setAlignment(Pos.CENTER);//позиционируем по центру
        hBox1.setSpacing(10);//расстояние между объектами внутри
        hBox1.setAlignment(Pos.CENTER);//позиционируем по центру

        return scene;
    }
    public void setError(String error) {
        this.error.setText(error);
    }
}

