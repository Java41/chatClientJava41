package org.example.chatclientjava41;

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
        error("Письмо с восстановлением пароля отправлено вам на почту");
        //иначе
        error("Неверный логин или пароль");
    }
    public void error(String messageError){
        view.error.setText(messageError);
    }
}
