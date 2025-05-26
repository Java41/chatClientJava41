package org.example.chatclientjava41;

public class RegistrationMenuController {

    private RegistrationMenuView view;
    private SceneNavigator sceneNavigator;

    public void setSceneNavigator(SceneNavigator sceneNavigator) {
        this.sceneNavigator = sceneNavigator;
    }

    public void setView(RegistrationMenuView view){
        this.view = view;
    }
    public void clickMenuAuth(){
        sceneNavigator.setAuth();
    }
    public void clickRegistration(String login, String password, String email){
        if (login == null || login.trim().isEmpty()){
            view.setError("Логин не может быть пустым");
        }
        if (password == null || password.length() < 4){
            view.setError("Пароль должен быть не менее 4 символов");
        }
        /*
        Здесь будет логика регистрации (Запрос к БД)
         */
        //Успешный ответ_______________________________
//         {
//        "id": "<long>",
//        "username": "<string>",
//        "email": "<string>",
//        "accessToken": "<string>",
//        "refreshToken": "<string>"
//        }
        //Неполные данные_______________________________
//        {
//        "error": "Email, пароль и дата рождения обязательны"
//        }
        //такой пользователь уже существует_______________________________
//        {
//        "error": "Пользователь с таким email уже существует"
//        }
        view.setError("");
    }
}
