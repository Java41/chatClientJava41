package org.example.chatclientjava41;

public class MainMenuController {
    private MainMenuView view;
    private SceneNavigator sceneNavigator;

    public void setView(MainMenuView view){
        this.view=view;
    }
    public void setSceneNavigator(SceneNavigator sceneNavigator){
        this.sceneNavigator=sceneNavigator;
    }
    public static void sendMessageField(String textMessage){
        System.out.println(AllResponse.SendMessage(1,textMessage));
    }
//_____________________________Выход пользователя_______________________________
    //Успешный ответ_______________________________
//        {
//        "message": "Logged out successfully"
//        }
    //Неверный refresh token_______________________________
//        {
//        "error": "Invalid refresh token"
//        }
    //_____________________________Обновление емейла_______________________________
    //Успешный ответ_______________________________
//        {
//        "id": "<long>",
//        "username": "<string>",
//        "email": "<string>",
//        "accessToken": "<string>",
//        "refreshToken": "<string>"
//        }
    //Неполные данные_______________________________
//        {
//        "error": "Email и пароль обязательны"
//        }
    //Неверные данные_______________________________
//        {
//        "error": "Неверные учетные данные"
//        }
    //такой пользователь уже существует_______________________________
//        {
//        "error": "Пользователь с таким email уже существует"
//        }
    //_____________________________Обновление имени пользователя_______________________________
    //Успешный ответ_______________________________
//        {
//        "id": "<long>",
//        "username": "<string>",
//        "email": "<string>",
//        "accessToken": "<string>",
//        "refreshToken": "<string>"
//        }
    //Неверные данные_______________________________
//        {
//        "error": "Username обязателен и должен начинаться с @"
//        }
    //Неверные данные_______________________________
//        {
//        "error": "Пользователь не найден"
//        }
    //такой пользователь уже существует_______________________________
//        {
//        "error": "Username уже занят"
//        }
    //_____________________________Получение сообщения_______________________________
    //Успешный ответ_______________________________
//        {
//        "id": 1,
//        "senderId": 1,
//        "senderUsername": "@DefaultUser",
//        "recipientId": 1,
//        "recipientUsername": "@DefaultRecipient",
//        "content": "Hi there!",
//        "timestamp": "2024-01-01T00:00:00"
//        }
    //Несуществует получателя_______________________________
//        {
//        "error": "Не указан ID получателя или текст сообщения"
//        }
    //Несуществует получателя_______________________________
//        {
//        "error": "Получатель не найден"
//        }
    //_____________________________Отправка сообщения_______________________________
    //Успешный ответ_______________________________
//         {
//        "id": 1,
//        "senderId": 1,
//        "senderUsername": "@DefaultUser",
//        "recipientId": 1,
//        "recipientUsername": "@DefaultRecipient",
//        "content": "Hi there!",
//        "timestamp": "2024-01-01T00:00:00"
//        },
//        {
//        "id": 1,
//        "senderId": 1,
//        "senderUsername": "@DefaultUser",
//        "recipientId": 1,
//        "recipientUsername": "@DefaultRecipient",
//        "content": "Hi there!",
//        "timestamp": "2024-01-01T00:00:00"
//        }
    //Неверный формат параметров since_______________________________
//        {
//        "error": "Неверный формат параметра 'since', используйте ISO 8601 (например, 2025-05-19T10:00:00)"
//        }
    //Пользователь не найден_______________________________
//        {
//        "error": "Пользователь не найден"
//        }
}
