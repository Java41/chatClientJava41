package org.example.chatclientjava41;

import java.io.IOException;

public class AuthorizationMenuController {
    private AuthorizationMenuView view;
    private SceneNavigator sceneNavigator;
    public void setView(AuthorizationMenuView view){
        this.view=view;
    }
    public void setSceneNavigator(SceneNavigator sceneNavigator){
        this.sceneNavigator=sceneNavigator;
    }
    public void clickMenuRegistration(){
        sceneNavigator.setRegistration();
    }
    public void clickMenuRestorePass(){
        sceneNavigator.setRestore();
    }
    public void clickEnter(String login,String password) {
        login.trim();
        password.trim();
        if (!(login.isEmpty()||password.isEmpty())){
            if(InputValidator.UserInputValidator.isEmailValid(login)||InputValidator.UserInputValidator.isLoginValid(login)){
                if (InputValidator.UserInputValidator.isLoginValid(password)){
                    view.setError(AllResponse.Authorisation(login,password));
                }else {view.setError("Недопустимые символы в поле Пароль");}
            }
            else {view.setError("Недопустимые символы в поле Логин");}
        }
        else {view.setError("Вы не ввели логин или пароль");}
    }
}
