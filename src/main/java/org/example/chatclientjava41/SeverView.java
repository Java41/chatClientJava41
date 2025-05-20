package org.example.chatclientjava41;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SeverView {
    private ViktorController viktorController;
    public Label error = new Label(" ");
    public SeverView(ViktorController viktorController) {
        this.viktorController = viktorController;
    }
    public Scene SeverScene() {

        TextField login = new TextField("Логин: ");
        Label nickname = new Label(" ");
        TextField password = new TextField("Пароль: ");
        Label You_password = new Label(" ");
        TextField email = new TextField("Email: ");
        Label el_Pochta = new Label(" ");
        Button menuAutorization = new Button();
        Button Registration = new Button();
        HBox hBox1 = new HBox(nickname, login);
        HBox hBox2 = new HBox(You_password, password);
        HBox hBox3 = new HBox(el_Pochta, email);
        VBox vBox = new VBox(hBox1, hBox2, hBox3, menuAutorization, Registration);
        Scene scene1 = new Scene(vBox, 200, 300);
        return scene1;
    }

}

