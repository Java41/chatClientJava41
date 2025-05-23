package org.example.chatclientjava41;

import javafx.stage.Stage;

public class SceneNavigator extends Stage {
    private RestoreMenuView restoreView;
    private AuthorizationMenuView authorizationView;
    private RegistrationMenuView registrationView;
    private MainMenuView mainView;
    public SceneNavigator() {
        RestoreMenuController restoreController=new RestoreMenuController();
        AuthorizationMenuController authorizationController=new AuthorizationMenuController();
        RegistrationMenuController registrationController=new RegistrationMenuController();
//        MainController mainController=new MainController();
        this.restoreView = new RestoreMenuView(restoreController);
        this.authorizationView = new AuthorizationMenuView(authorizationController);
        this.registrationView = new RegistrationMenuView(registrationController);
//        this.mainView = new MainView(mainController);
        restoreController.setView(restoreView);
        restoreController.setSceneNavigator(this);
        authorizationController.setView(authorizationView);
        authorizationController.setSceneNavigator(this);
        registrationController.setView(registrationView);
        registrationController.setSceneNavigator(this);
//        mainController.setView(mainView);
//        mainController.setSceneNavigator(this);
        this.setScene(authorizationView.AuthorizationScene());
        this.setTitle("Авторизация");
        this.show();
    }
    public void setAuth(){
        this.setScene(authorizationView.AuthorizationScene());
        this.setTitle("Авторизация");
    }
    public void setRestore(){
        this.setScene(restoreView.RestoreScene());
        this.setTitle("Восстановление пароля");
    }
//    public void setDefault(){
//        this.setScene(mainView.MainScene());
//        this.setTitle("Чат41");
//    }
    public void setRegistration(){
        this.setScene(registrationView.RegistrationScene());
        this.setTitle("Регистрация");
    }
}
