package org.example.chatclientjava41;

public class DaniilController {
    private DaniilView view;
    private SceneNavigator sceneNavigator;
    public void setView(DaniilView view){
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
    public void clickEnter(String login,String password){
        //запрос в БД если Истина то
//        sceneNavigator.setDefault();
        //иначе
        error("Неверный логин или пароль");
    }
    public void error(String messageError){
        view.error.setText(messageError);
    }
}
