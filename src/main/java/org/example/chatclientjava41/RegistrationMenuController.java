package org.example.chatclientjava41;

import java.io.IOException;
import java.time.LocalDate;

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
    public void clickRegistration(String login, String password, String email) {
        if (login.isEmpty() || password.isEmpty() || email.isEmpty()) {
            view.setError("Все поля обязательны");
            return;
        }

        if(!(InputValidator.UserInputValidator.isLoginValid(login))){
            view.setError("Длина логина: 3-20 символов");
            return;
        }

        if (!(InputValidator.UserInputValidator.isPasswordValid(password))){
            view.setError("Пароль не меньше 8 символов, с заглавной, строчной, цифрой и спецсимволом");
            return;
        }

        if (!(InputValidator.UserInputValidator.isEmailValid(email))){
            view.setError("Неверный формат Email");
            return;
        }

        String resp = AllResponse.RegistrationUser(email, login, password, LocalDate.now().toString());
        view.setError(resp);

        if ("Регистрация прошла успешно".equals(resp)){
            sceneNavigator.setAuth();
        }

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

    }
}
