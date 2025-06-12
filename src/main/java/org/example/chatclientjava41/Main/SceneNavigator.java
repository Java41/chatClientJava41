package org.example.chatclientjava41.Main;

import javafx.stage.Stage;
import org.example.chatclientjava41.UIComponents.Controller.AuthorizationMenuController;
import org.example.chatclientjava41.UIComponents.Controller.MainMenuController;
import org.example.chatclientjava41.UIComponents.Controller.RegistrationMenuController;
import org.example.chatclientjava41.UIComponents.Controller.RestoreMenuController;
import org.example.chatclientjava41.UIComponents.View.AuthorizationMenuView;
import org.example.chatclientjava41.UIComponents.View.MainMenuView;
import org.example.chatclientjava41.UIComponents.View.RegistrationMenuView;
import org.example.chatclientjava41.UIComponents.View.RestoreMenuView;

public class SceneNavigator extends Stage {
    private final RestoreMenuView restoreView;
    private final AuthorizationMenuView authorizationView;
    private final RegistrationMenuView registrationView;
    private MainMenuView mainView;
    public SceneNavigator() {
        RestoreMenuController restoreController=new RestoreMenuController();
        AuthorizationMenuController authorizationController=new AuthorizationMenuController();
        RegistrationMenuController registrationController=new RegistrationMenuController();
        MainMenuController mainMenuController=new MainMenuController();
        this.restoreView = new RestoreMenuView(restoreController);
        this.authorizationView = new AuthorizationMenuView(authorizationController);
        this.registrationView = new RegistrationMenuView(registrationController);
        this.mainView = new MainMenuView(mainMenuController);
        restoreController.setView(restoreView);
        restoreController.setSceneNavigator(this);
        authorizationController.setView(authorizationView);
        authorizationController.setSceneNavigator(this);
        registrationController.setView(registrationView);
        registrationController.setSceneNavigator(this);
        mainMenuController.setView(mainView);
        mainMenuController.setSceneNavigator(this);
    }

    public void MainViewUpdate() {
        mainView.CenterShardChat();
    }

    public void setAuth(){
        this.setScene(authorizationView.AuthorizationScene());
        this.setTitle("Авторизация");
    }
    public void setRestore(){
        this.setScene(restoreView.RestoreScene());
        this.setTitle("Восстановление пароля");
    }
    public void setMain(long id){
        mainView.setUserId(id);
        this.setScene(mainView.MainScene());
        this.setTitle("Чат41");

    }
    public void setRegistration(){
        this.setScene(registrationView.RegistrationScene());
        this.setTitle("Регистрация");
    }
}
