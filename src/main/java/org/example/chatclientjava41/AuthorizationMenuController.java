package org.example.chatclientjava41;
import com.fasterxml.jackson.databind.ObjectMapper;


public class AuthorizationMenuController {
    private AuthorizationMenuView view;
    private SceneNavigator sceneNavigator;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void setView(AuthorizationMenuView view) {
        this.view = view;
    }

    public void setSceneNavigator(SceneNavigator sceneNavigator) {
        this.sceneNavigator = sceneNavigator;
    }

    public void clickMenuRegistration() {
        sceneNavigator.setRegistration();
    }

    public void clickMenuRestorePass() {
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

//Александр я пока закомментил твой труд) лучше пусть состояние ApplicationState при приеме токенов решает о том будет ли открыто основное окно или авторизация,
// т.к. с холодного запуска может быть ситуация что токены уже есть и окно авторизации даже не откроется как и его контроллер
// да и просто не хотелось бы чтоб все вокруг в кучу мешалось
        //а вот с кучей запросов былоб топ помочь
//     public void clickEnter(String login, String password) {
//         try {
//             System.out.println("Попытка входа: " + login);
//             String response = AllResponse.login(login, password);
//             JsonNode json = objectMapper.readTree(response);

//             appState.updateAuthState(
//                     json.get("accessToken").asText(),
//                     json.get("refreshToken").asText(),
//                     new LinkedHashMap<>(),
//                     900 // Время жизни токена (минут)
//             );
//             System.out.println("""
//                     Авторизация прошла успешно!
                    
//                     Далее нужно парсить полученный accessToken можно это сделать
//                     в singleton поскольку все извлеченные данные там будут храниться
//                     и извлекать из него данные о пользователе.
//                     Что бы при переходе на следующую сцену их можно было использовать.

//                     И следующий шаг- переход на сцену которая защищена авторизацией.
//                     """);
// //            sceneNavigator.setDefault();
//         } catch (IOException e) {
//             System.out.println("Ошибка сети: " + e.getMessage());
//             view.error.setText("Ошибка сети: " + e.getMessage());
//         } catch (Exception e) {
//             view.error.setText("Ошибка обработки: " + e.getMessage());
//             System.out.println("Ошибка обработки: " + e.getMessage());
//         }

    }
}
