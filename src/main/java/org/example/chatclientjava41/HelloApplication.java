package org.example.chatclientjava41;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private Stage primaryStage; //Неиспользованная, для перехода на предыдущие сцены
    private BorderPane rootLayout;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        rootLayout = new BorderPane();
        createMenuBar();

        showScene1();

        Scene scene = new Scene(rootLayout, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Scene Switcher");
        stage.show();
    }

    private void createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu scenesMenu = new Menu("Scenes");
        MenuItem scene1Item = new MenuItem("Scene 1");
        MenuItem scene2Item = new MenuItem("Scene 2");
        MenuItem scene3Item = new MenuItem("Auth scene");
        MenuItem scene8Item = new MenuItem("Scene Dev8");

        scene1Item.setOnAction(e -> showScene1());
        scene2Item.setOnAction(e -> showScene2());
        scene3Item.setOnAction(e -> authorizationScene());
        scene8Item.setOnAction(e -> showSceneDev8());


        scenesMenu.getItems().addAll(scene1Item, scene2Item, scene3Item, scene8Item);
        menuBar.getMenus().add(scenesMenu);

        rootLayout.setTop(menuBar);
    }

    private void showScene1() {
        VBox content = new VBox(10,
                new Label("Это сцена 1"),
                new Button("Изменение в кнопке еще")
        );
        content.setAlignment(Pos.CENTER);
        rootLayout.setCenter(content);
    }

    private void showScene2() {
        VBox content = new VBox(10,
                new Label("Это сцена 2")
        );
        content.setAlignment(Pos.CENTER);
        rootLayout.setCenter(content);
    }

    private void authorizationScene() {
        String login = "";

        TextField loginField = new TextField(login);
        PasswordField passField = new PasswordField();

        loginField.setMaxWidth(100);
        passField.setMaxWidth(100);

        VBox content = new VBox(10,
                new Label("Авторизация"),
                loginField,
                passField,
                new Button("Войти")
        );

        content.setAlignment(Pos.CENTER);
        rootLayout.setCenter(content);
    }

    private void showSceneDev8() {
        //Aleksandr Borodavkin
        VBox content = new VBox(10,
                new Label("Это сцена Dev8"),
                new Button(" Это кнопка Dev8")
        );
        content.setAlignment(Pos.CENTER);
        rootLayout.setCenter(content);
    }

    public static void main(String[] args) {
        launch();
    }
}