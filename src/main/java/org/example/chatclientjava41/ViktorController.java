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

    private void clickMenuAuth(){
        sceneNavigator.setAuth();
    }

    private void clickRegistration(){
        String login = view.loginField.getText().trim();
        String password = view.passwordField.getText();

        if (login.isEmpty()){
            view.error.setText("Логин не может быть пустым");
            return;
        }
        if (password.length() < 4){
            view.error.setText("Пароль должен быть не менее 4 символов");
            return;
        }

        /*
        Здесь будет логика регистрации (Запрос к БД)
         */

        view.error.setText("");

        sceneNavigator.setDefault;
    }

}
