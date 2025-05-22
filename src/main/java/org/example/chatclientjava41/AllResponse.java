package org.example.chatclientjava41;
import okhttp3.*;

import java.io.IOException;

public class AllResponse {

    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
    MediaType mediaType = MediaType.parse("application/json");
    RequestBody body = RequestBody.create(mediaType, "{\n  \"email\": \"user@example.com\",\n  \"password\": \"password123\"\n}");
    Request request = new Request.Builder()
            .url("//auth/login")
            .method("POST", body)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build();
    Response response = client.newCall(request).execute();

    public AllResponse() throws IOException {
    }
}
//_____________________________Аутентификация пользователя_______________________________
//
//MediaType mediaType = MediaType.parse("application/json");
//RequestBody body = RequestBody.create(mediaType, "{\n  \"email\": \"user@example.com\",\n  \"password\": \"password123\"\n}");
//Request request = new Request.Builder()
//        .url("//auth/login")
//        .method("POST", body)
//        .addHeader("Content-Type", "application/json")
//        .addHeader("Accept", "application/json")
//        .build();
//Response response = client.newCall(request).execute();
            //Успешный ответ_______________________________
//        {
//        "id": "<long>",
//        "username": "<string>",
//        "email": "<string>",
//        "accessToken": "<string>",
//        "refreshToken": "<string>"
//        }
            //Отсутствует пароль или почта_______________________________
//        {
//        "error": "Email и пароль обязательны"
//        }
            //Неверные учетные данные_______________________________
//        {
//        "error": "Неверные учетные данные"
//        }
//_____________________________Выход пользователя_______________________________
//
//MediaType mediaType = MediaType.parse("application/json");
//RequestBody body = RequestBody.create(mediaType, "{\n  \"refreshToken\": \"f47ac10b-58cc-4372-a567-0e02b2c3d479\"\n}");
//Request request = new Request.Builder()
//        .url("//auth/logout")
//        .method("POST", body)
//        .addHeader("Content-Type", "application/json")
//        .addHeader("Accept", "application/json")
//        .build();
//Response response = client.newCall(request).execute();
            //Успешный ответ_______________________________
//        {
//        "message": "Logged out successfully"
//        }
            //Неверный refresh token_______________________________
//        {
//        "error": "Invalid refresh token"
//        }
//_____________________________Обновление пользователя_______________________________
//
//MediaType mediaType = MediaType.parse("application/json");
//RequestBody body = RequestBody.create(mediaType, "{\n  \"refreshToken\": \"f47ac10b-58cc-4372-a567-0e02b2c3d479\"\n}");
//Request request = new Request.Builder()
//        .url("//auth/refresh")
//        .method("POST", body)
//        .addHeader("Content-Type", "application/json")
//        .addHeader("Accept", "application/json")
//        .build();
//Response response = client.newCall(request).execute();
        //Успешный ответ_______________________________
//        {
//        "id": "<long>",
//        "username": "<string>",
//        "email": "<string>",
//        "accessToken": "<string>",
//        "refreshToken": "<string>"
//        }
        //Неверный refresh token_______________________________
//        {
//        "error": "Refresh token обязателен"
//        }
        //Неверный refresh token_______________________________
//        {
//        "error": "Недействительный или истекший refresh token"
//        }
//_____________________________Регистрация пользователя_______________________________
//
//MediaType mediaType = MediaType.parse("application/json");
//RequestBody body = RequestBody.create(mediaType, "{\n  \"email\": \"user@example.com\",\n  \"password\": \"password123\",\n  \"birthdate\": \"1990-01-01\"\n}");
//Request request = new Request.Builder()
//        .url("//auth/register")
//        .method("POST", body)
//        .addHeader("Content-Type", "application/json")
//        .addHeader("Accept", "application/json")
//        .build();
//Response response = client.newCall(request).execute();
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
//_____________________________Обновление емейла_______________________________
//
//MediaType mediaType = MediaType.parse("application/json");
//RequestBody body = RequestBody.create(mediaType, "{\n  \"email\": \"new.email@example.com\",\n  \"password\": \"currentPassword123\"\n}");
//Request request = new Request.Builder()
//        .url("//auth/update-email")
//        .method("POST", body)
//        .addHeader("Content-Type", "application/json")
//        .addHeader("Accept", "application/json")
//        .addHeader("Authorization", "••••••")
//        .build();
//Response response = client.newCall(request).execute();
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
//
//MediaType mediaType = MediaType.parse("application/json");
//RequestBody body = RequestBody.create(mediaType, "{\n  \"username\": \"@NewUser_123\"\n}");
//Request request = new Request.Builder()
//        .url("//auth/update-username")
//        .method("POST", body)
//        .addHeader("Content-Type", "application/json")
//        .addHeader("Accept", "application/json")
//        .addHeader("Authorization", "••••••")
//        .build();
//Response response = client.newCall(request).execute();
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
//
//MediaType mediaType = MediaType.parse("text/plain");
//RequestBody body = RequestBody.create(mediaType, "");
//Request request = new Request.Builder()
//        .url("//messages?since=1970-01-01T00:00:00&with=<long>")
//        .method("GET", body)
//        .addHeader("Accept", "application/json")
//        .addHeader("Authorization", "••••••")
//        .build();
//Response response = client.newCall(request).execute();
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
//MediaType mediaType = MediaType.parse("text/plain");
//RequestBody body = RequestBody.create(mediaType, "");
//Request request = new Request.Builder()
//        .url("//messages?since=1970-01-01T00:00:00&with=<long>")
//        .method("GET", body)
//        .addHeader("Accept", "application/json")
//        .addHeader("Authorization", "••••••")
//        .build();
//Response response = client.newCall(request).execute();
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
//_____________________________Получить всех пользователей_______________________________
//
//MediaType mediaType = MediaType.parse("text/plain");
//RequestBody body = RequestBody.create(mediaType, "");
//Request request = new Request.Builder()
//        .url("//contacts")
//        .method("GET", body)
//        .addHeader("Accept", "application/json")
//        .addHeader("Authorization", "••••••")
//        .build();
//Response response = client.newCall(request).execute();