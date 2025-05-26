package org.example.chatclientjava41;

import javafx.application.Application;
import javafx.stage.Stage;

public class Chat41Client extends Application {

    //    private BorderPane rootLayout;
    @Override
    public void start(Stage stage) {
        ApplicationState applicationState = ApplicationState.getApplicationState();
        applicationState.updateAuthState("accessToken","refreshToken");//хз мы же гдето должны хранить данные о наличии токенов, если да, то тут должна быть загрузка стартовых данных приложения
        Stage primaryStage = applicationState.getSceneNavigator();//состояние приложения должно управлять диспечером окон или мб не давать весь диспечер окон а только текущее окно??
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    //Создание компонента меню
//    private void createMenuBar() {
//        MenuBar menuBar = new MenuBar();
//
//        Menu scenesMenu = new Menu("Scenes");
//        MenuItem scene1Item = new MenuItem("Scene 1");
//        MenuItem scene3Item = new MenuItem("Auth scene");
//        MenuItem scene8Item = new MenuItem("Scene Dev8");
//        MenuItem scene2Item = new MenuItem("Scene 2 Виктор");
//        MenuItem scene4Item = new MenuItem("Scene Даниил");
//
//        scene1Item.setOnAction(e -> showScene1());
//        scene2Item.setOnAction(e -> showSceneDev7Viktor());
//        scene2Item.setOnAction(e -> showScene2());
//        scene8Item.setOnAction(e -> showSceneDev8());
//        scene3Item.setOnAction(e -> authorizationScene());
//        scenesMenu.getItems().addAll(scene1Item, scene2Item, scene3Item, scene8Item,scene4Item);
//
//        menuBar.getMenus().add(scenesMenu);
//
//        rootLayout.setTop(menuBar);
//    }
}
