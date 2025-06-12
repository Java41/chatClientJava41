package org.example.chatclientjava41;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.example.chatclientjava41.Data.ApplicationState;

public class Chat41Client extends Application {

    @Override
    public void start(Stage stage) {
        ApplicationState applicationState = ApplicationState.getApplicationState();
        applicationState.responsePublicKey();//получение публичного ключа для проверки достоверности токенов
        applicationState.updateAuthState(new String[]{"accessToken","refreshToken"});//хз мы же гдето должны хранить данные о наличии токенов, если да, то тут должна быть загрузка стартовых данных приложения
        Stage primaryStage = applicationState.getSceneNavigator();//состояние приложения должно управлять диспечером окон или мб не давать весь диспечер окон а только текущее окно??
        primaryStage.setOnCloseRequest(windowEvent -> Platform.runLater(applicationState::stopUpdateMessagesAndContacts));
        primaryStage.setX((Screen.getPrimary().getVisualBounds().getWidth() / 2)-600);
        primaryStage.setY((Screen.getPrimary().getVisualBounds().getHeight() / 2)-250);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
