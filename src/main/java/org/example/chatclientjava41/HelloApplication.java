package org.example.chatclientjava41;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private Stage primaryStage;
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

        scene1Item.setOnAction(e -> showScene1());

        scenesMenu.getItems().addAll(scene1Item);
        menuBar.getMenus().add(scenesMenu);

        rootLayout.setTop(menuBar);
    }

    private void showScene1() {
        VBox content = new VBox(10,
                new Label("Это сцена 1"),
                new Button("Просто кнопка")
        );
        content.setAlignment(Pos.CENTER);
        rootLayout.setCenter(content);
    }

    public static void main(String[] args) {
        launch();
    }
}