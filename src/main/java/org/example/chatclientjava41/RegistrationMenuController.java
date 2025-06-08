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
    public void clickRegistration(String email,String password, String date, String firstname, String lastname) {
        if (email.isEmpty() || password.isEmpty() || date.isEmpty()|| firstname.isEmpty()|| lastname.isEmpty()) {
            view.setError("Все поля обязательны");
            return;
        }

//        if (!(InputValidator.UserInputValidator.isPasswordValid(password))){
//            view.setError("Пароль не меньше 8 символов, с заглавной, строчной, цифрой и спецсимволом");
//            return;
//        }

        if (!(InputValidator.UserInputValidator.isEmailValid(email))){
            view.setError("Неверный формат Email");
            return;
        }

        String resp = AllResponse.RegistrationUser(email, password, date,firstname,lastname);
        view.setError(resp);

        if ("Регистрация прошла успешно".equals(resp)){
            sceneNavigator.setAuth();
        }
    }
}
