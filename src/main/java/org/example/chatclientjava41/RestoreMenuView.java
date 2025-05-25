package org.example.chatclientjava41;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RestoreMenuView{
    private RestoreMenuController restoreController;
    private Label error=new Label("");
    public RestoreMenuView(RestoreMenuController restoreController) {
        this.restoreController = restoreController;
    }
    public Scene RestoreScene(){
        VBox vBox=new VBox();//сначала объявляем vbox и scene применяем к сцене стиль, потом запаковываем кнопки и прочее
        Scene scene = new Scene(vBox, 500, 250);
        scene.getStylesheets().add(getClass().getResource("/StyleRestoreMenu.css").toExternalForm());
        TextField email=new TextField();
        email.setPromptText("Email");
        Button restoreButton=new Button("Восстановить пароль");
        Button authMenuButton=new Button("Назад к авторизации");
        authMenuButton.getStyleClass().add("secondary");//так можно присвоить другой стиль который есть в файле .css, еслиб не присвоил былаб кнопка(по умолчанию для стиля)-черная
        restoreButton.setOnAction(actionEvent ->restoreController.clickRestore());
        authMenuButton.setOnAction(actionEvent ->restoreController.clickMenuAuth());
        vBox.getChildren().add(email);//упаковываем все элементы в vbox
        vBox.getChildren().add(restoreButton);
        vBox.getChildren().add(authMenuButton);
        vBox.getChildren().add(error);
        vBox.setSpacing(10);//расстояние между объектами внутри
        vBox.setAlignment(Pos.CENTER);//позиционируем по центру
        return scene;
    }
    public void setError(String error) {
        this.error.setText(error);
    }
}
