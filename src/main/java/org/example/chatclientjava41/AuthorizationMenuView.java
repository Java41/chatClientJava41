package org.example.chatclientjava41;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AuthorizationMenuView {
    private Label error = new Label("");
    private final AuthorizationMenuController daniilController;

    public AuthorizationMenuView ( AuthorizationMenuController daniilController ) {
        this.daniilController = daniilController;
    }
    public Scene AuthorizationScene () {
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox, 500, 250);
        // логин и пароль захардкожены для удобства)
        TextField login = new TextField("user@example.com");
        login.setPromptText("Логин:");
        TextField password = new TextField("password123");
        password.setPromptText("Пароль:");
        scene.getStylesheets().add(getClass().getResource("/StyleRestoreMenu.css").toExternalForm());
        Button logInButton = new Button("Войти");
        Button registrationMenuButton = new Button("Регистрация");
        Button restoreMenuButton = new Button("Восстановить пароль");
        restoreMenuButton.getStyleClass().add("secondary");
        logInButton.setOnAction(
                actionEvent -> daniilController.clickEnter(
                        login.getText(),
                        password.getText())
        );
        registrationMenuButton.setOnAction(actionEvent -> daniilController.clickMenuRegistration());
        restoreMenuButton.setOnAction(actionEvent -> daniilController.clickMenuRestorePass());
        HBox hbox1 = new HBox(logInButton,registrationMenuButton);
        hbox1.setSpacing(10);//расстояние между объектами внутри
        hbox1.setAlignment(Pos.CENTER);
        vbox.getChildren().add(login);//упаковываем все элементы в vbox
        vbox.getChildren().add(password);
        vbox.getChildren().add(hbox1);
        vbox.getChildren().add(restoreMenuButton);
        vbox.getChildren().add(error);
        vbox.setAlignment(Pos.CENTER);//позиционируем по центру
        vbox.setSpacing(10);
        return scene;
    }

    public void setError(String error) {
        this.error.setText(error);
    }
}
