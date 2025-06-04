package org.example.chatclientjava41;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;

public class AllResponse {
    private static final ApplicationState applicationState = ApplicationState.getApplicationState();//управление состоянием будут осуществлять только ответы с сервера
    private static final String SERVER_URL = "https://java41.ru/";
    private static final String PUB_KEY_PATH = "public-key";
    private static final String LOGIN_PATH = "auth/login";
    private static final String LOGOUT_PATH = "auth/logout";
    private static final String REFRESH_PATH = "auth/refresh";
    private static final String REGISTRATION_PATH = "auth/register";
    private static final String UPDATE_EMAIL = "auth/update-email";
    private static final String UPDATE_NAME = "auth/update-username";
    private static final String SEND_MESSAGE = "messages";
    private static final String CONTACTS_PATH = "contacts";
    private static final String JSON_MEDIA = "application/json";
    private static final OkHttpClient client = new OkHttpClient().newBuilder().build();

    public static String getPublicKey() {
        Request request = new Request.Builder()
                .url(SERVER_URL + PUB_KEY_PATH)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : null;
            if (responseBody.contains("-----BEGIN PUBLIC KEY-----") && responseBody.contains("-----END PUBLIC KEY-----")) {
                responseBody = responseBody.replace("-----BEGIN PUBLIC KEY-----", "")
                        .replace("-----END PUBLIC KEY-----", "").replaceAll("\\s", "");
                return responseBody;
            } else return "Публичный ключ не был получен";
        } catch (IOException e) {
            return "Ошибка";
        }
    }

    public static String Authorisation(String login, String password) {//при положительном ответе данные в состояние приложения на updateAuthState(String accessToken, String refreshToken)
        MediaType mediaType = MediaType.parse(JSON_MEDIA);
        RequestBody body = RequestBody.create(mediaType, "{\n  \"email\": \"" + login + "\",\n  \"password\": \"" + password + "\"\n}");
        Request request = new Request.Builder()
                .url(SERVER_URL + LOGIN_PATH)
                .post(body)
                .header("Content-Type", JSON_MEDIA)
                .header("Accept", JSON_MEDIA)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (responseBody.contains("\"accessToken\":\"") && responseBody.contains("\",\"refreshToken\":\"")) {
                responseBody = responseBody.replaceAll("[{}\"]", "");
                responseBody = responseBody.replace("accessToken:", "");
                String[] tokens = responseBody.split(",refreshToken:");
                applicationState.updateAuthState(tokens);
                return "Вход успешно состоялся";
            } else return "Неверный логин или пароль";

        } catch (IOException e) {
            return "Error auth";
        }
    }
    //_____________________________Выход пользователя_______________________________
    public static void Logout() {
        MediaType mediaType = MediaType.parse(JSON_MEDIA);
        RequestBody body = RequestBody.create(mediaType, "{\n  \"refreshToken\": \"" + applicationState.getRefreshToken() + "\"\n}");
        Request request = new Request.Builder()
                .url(SERVER_URL + LOGOUT_PATH)
                .method("POST", body)
                .addHeader("Content-Type", JSON_MEDIA)
                .addHeader("Accept", JSON_MEDIA)
                .build();
        try (Response response = client.newCall(request).execute();){
            if(response.code()==200){
                applicationState.LogOut();
            }else System.out.println("чет запрос не сработал");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    //_____________________________Обновление токена_______________________________
    public static void RefreshToken() {
        MediaType mediaType = MediaType.parse(JSON_MEDIA);
        RequestBody body = RequestBody.create(mediaType, "{\n  \"refreshToken\": \"" + applicationState.getRefreshToken() + "\"\n}");
        Request request = new Request.Builder()
                .url(SERVER_URL + REFRESH_PATH)
                .method("POST", body)
                .addHeader("Content-Type", JSON_MEDIA)
                .addHeader("Accept", JSON_MEDIA)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            if (response.code() == 400) {
                System.out.println(response.code() + " " + response.message());
            } else if (response.code() == 401) {
                applicationState.updateAuthState(null);
            }else {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                String accessToken = jsonNode.get("accessToken").asText();
                String refreshToken = jsonNode.get("refreshToken").asText();
                applicationState.updateAuthState(new String[]{accessToken, refreshToken});
            }
        } catch (IOException e) {
            System.out.println("Ошибка получения токена");
        }
    }

    //_____________________________Регистрация пользователя_______________________________
    public static String RegistrationUser(String email, String login, String password, String date) {
        MediaType mediaType = MediaType.parse(JSON_MEDIA);
        RequestBody body = RequestBody.create(mediaType, "{\n  \"email\": \"" + email + "\",\n  \"login\": \"" + login + "\",\n  \"password\": \"" + password + "\",\n  \"birthdate\": \"" + date + "\"\n}");
        Request request = new Request.Builder()
                .url(SERVER_URL + REGISTRATION_PATH)
                .method("POST", body)
                .addHeader("Content-Type", JSON_MEDIA)
                .addHeader("Accept", JSON_MEDIA)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (response.code() == 201) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                String accessToken = jsonNode.get("accessToken").asText();
                String refreshToken = jsonNode.get("refreshToken").asText();
                applicationState.updateAuthState(new String[]{accessToken, refreshToken});
                return "Регистрация прошла успешна";
            }
            return "Ошибка регистрации: " + response.code();
        } catch (IOException e) {
            return "error registration";
        }
        //Способ выше подглядел у гпт, насколько я понял всю рутину ковыряния json передаем Jackson-у
        // ObjectMapper это основной класс Jackson для преобразования между Json и Java
        // JsonNode — это узел (node) в дереве JSON, полученном через ObjectMapper.readTree(). С его помощью можно «гулять» по структуре ответа без создания дополнительных DTO-классов.

        //Ниже второй вариант, пока закомментил
        /*
        try (Response resp = client.newCall(request).execute()) {
        String body = resp.body().string();
        if (resp.code() == 201 && body.contains("accessToken")) {
            // повторяем логику replaceAll/split из Authorisation
            String cleaned = body.replaceAll("[{}\"]","")
                               .replace("accessToken:","");
            String[] tokens = cleaned.split(",refreshToken:");
            applicationState.updateAuthState(tokens);
            return "Регистрация прошла успешно";
        }
        return "Ошибка регистрации: " + resp.code();
    } catch (IOException e) {
        return "error registration";
    }
         */
    }

    public static void updateProfileFieldsAPI(String jsonData) {
        RequestBody body = RequestBody.create(
                jsonData,
                MediaType.parse(JSON_MEDIA)
        );

        Request request = new Request.Builder()
                .url(SERVER_URL + "/profile")
                .patch(body)
                .addHeader("Content-Type", JSON_MEDIA)
                .addHeader("Accept", JSON_MEDIA)
                .addHeader("Authorization", "Bearer " + applicationState.getAccessToken())
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println(response.code() + " " + responseBody);

            if (responseBody.contains("\"accessToken\":\"") && responseBody.contains("\",\"refreshToken\":\"")) {
                responseBody = responseBody.replaceAll("[{}\"]", "");
                responseBody = responseBody.replace("accessToken:", "");
                String[] tokens = responseBody.split(",refreshToken:");
                applicationState.updateAuthState(tokens);
                System.out.println("Попытка обновить поле удалась так как ответ содержит токен");
            } else {
                System.out.println(response.code() + " " + responseBody);
            }
        } catch (Exception e) {
            System.out.println("Ошибка обновления профиля: " + e.getMessage());
        }
    }

    //_____________________________Отправка сообщения_______________________________
    public static String SendMessage(Integer recipientId, String message) {
        MediaType mediaType = MediaType.parse(JSON_MEDIA);
        RequestBody body = RequestBody.create(mediaType, "{\n  \"recipientId\":" + recipientId + ",\n  \"content\": \"" + message + "\"\n}");
        Request request = new Request.Builder()
                .url(SERVER_URL + SEND_MESSAGE)
                .method("POST", body)
                .addHeader("Content-Type", JSON_MEDIA)
                .addHeader("Accept", JSON_MEDIA)
                .addHeader("Authorization", "Bearer " + applicationState.getAccessToken())
                .build();
        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println(response.code());
            if (response.code() == 201) {
                return "Сообщение отправлено";
            } else if (response.code() == 401) {
                return "Вы не авторизованы";
            } else if (response.code() == 403) {
                return "Доступ запрещен";
            } else return "Получатель не найден";

        } catch (IOException e) {
            return "Error auth";
        }
    }
//_____________________________Получение сообщения_______________________________
public static String GetMessage() {
    Request request = new Request.Builder()
            .url(SERVER_URL + SEND_MESSAGE+"?since="+applicationState.getLastTimeResponseMassage()+"&with=<long>")//data="1970-01-01T00:00:00" хз че за лонг число в постмане
            .method("GET", null)
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "Bearer " + applicationState.getAccessToken())
            .build();
    try (Response response = client.newCall(request).execute()) {
        String responseBody = response.body().string();
        if (response.code()==200){
            return responseBody;
        } else System.out.println("Error get messages");

    } catch (IOException e) {
        System.out.println("Error auth");
    }
    return null;
}
//_____________________________Получить все контакты_______________________________
public static void getAllContacts(){
    Request request = new Request.Builder()
            .url(SERVER_URL+CONTACTS_PATH)
            .method("GET", null)
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "Bearer " + applicationState.getAccessToken())
            .build();
        try (Response response = client.newCall(request).execute()) {
        String responseBody = response.body().string();
        if (response.code()==200){
            Contact.createContact(responseBody);
            System.out.println("got it");
        } else System.out.println("Error get contacts");

        } catch (IOException e) {
        System.out.println("Error auth");
        }
    }
    //_________________________Добавить контакт__________________________________
    public static void AddContact(String id){
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "{\n  \"id\":" + id + "\n}");
        Request request = new Request.Builder()
                .url(SERVER_URL+CONTACTS_PATH)
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + applicationState.getAccessToken())
                .addHeader("Accept", JSON_MEDIA)
                .build();
        try(Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println(responseBody);
            if (response.code()==201){
                System.out.println("contact add");
                Contact.createContact(responseBody);

            } else if(response.code()==404||response.code()==400){
                System.out.println("Такого пользователя не существует");
            }else if(response.code()==409){
                System.out.println("Переключение на уже существующий контакт");
            }

        } catch (IOException e) {
            System.out.println("Error auth");
        }
    }
}