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
        MainMenuController mainMenuController=new MainMenuController();
        this.restoreView = new RestoreMenuView(restoreController);
        this.authorizationView = new AuthorizationMenuView(authorizationController);
        this.registrationView = new RegistrationMenuView(registrationController);
      //  this.mainView = new MainMenuView(mainMenuController);
        restoreController.setView(restoreView);
        restoreController.setSceneNavigator(this);
        authorizationController.setView(authorizationView);
        authorizationController.setSceneNavigator(this);
        registrationController.setView(registrationView);
        registrationController.setSceneNavigator(this);
        mainMenuController.setView(mainView);
        mainMenuController.setSceneNavigator(this);
    }
    public void setAuth(){
        this.setScene(authorizationView.AuthorizationScene());
        this.setTitle("Авторизация");
    }
    public void setRestore(){
        this.setScene(restoreView.RestoreScene());
        this.setTitle("Восстановление пароля");
    }
//    public void setMain(String login,String password){
    // запрос на сервер
    // Успешный ответ_______________________________
//        {
//        "id": "<long>",
//        "username": "<string>",
//        "email": "<string>",
//        "accessToken": "<string>",
//        "refreshToken": "<string>"
//        }
//        this.setScene(mainView.MainScene());
//        this.setTitle("Чат41");
    //иначе
    //authorizationView.error.setText("неверные учетные данные");
        //Неверные учетные данные_______________________________
//        {
//        "error": "Неверные учетные данные"
//        }
//    }
    public void setRegistration(){
        this.setScene(registrationView.RegistrationScene());
        this.setTitle("Регистрация");

    }
}
