package org.example.chatclientjava41.UIComponents.Controller;

import org.example.chatclientjava41.Main.SceneNavigator;
import org.example.chatclientjava41.UIComponents.View.RestoreMenuView;

public class RestoreMenuController {
    private RestoreMenuView view;
    private SceneNavigator sceneNavigator;
    public void setView(RestoreMenuView view){
        this.view=view;
    }
    public void setSceneNavigator(SceneNavigator sceneNavigator){
        this.sceneNavigator=sceneNavigator;
    }
    public void clickMenuAuth(){
        sceneNavigator.setAuth();
    }
    public void clickRestore(){
//        запрос в БД если Истина то
        view.setError("Письмо с восстановлением пароля отправлено вам на почту");
        //иначе
        view.setError("Неверный логин или пароль");
    }
}
