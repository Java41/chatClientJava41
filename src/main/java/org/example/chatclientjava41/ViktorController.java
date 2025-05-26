package org.example.chatclientjava41;

public class ViktorController {

    private SeverView view;
    private SceneNavigator sceneNavigator;

    public void setSceneNavigator(SceneNavigator sceneNavigator) {
        this.sceneNavigator = sceneNavigator;
    }

    public void setView(SeverView view){
        this.view = view;
    }

    public void clickMenuAuth(){
        sceneNavigator.setAuth();
    }
    //ы
    public void clickRegistration(String login, String password, String email){


        if (login == null || login.trim().isEmpty()){
            view.error.setText("Логин не может быть пустым");
            return;
        }
        if (password == null || password.length() < 4){
            view.error.setText("Пароль должен быть не менее 4 символов");
            return;
        }

        /*
        Здесь будет логика регистрации (Запрос к БД)
         */

        view.error.setText("");

//        sceneNavigator.setDefault();
    }

}
