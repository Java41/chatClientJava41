package org.example.chatclientjava41;
import javafx.scene.control.Label;
import okhttp3.*;

import java.io.IOException;

public class AllResponse {
    private static final String SERVER_URL = "https://java41.ru/";
    private static final String PUB_KEY_PATH = "public-key";
    private static final String LOGIN_PATH = "auth/login";
    private static final String LOGOUT_PATH= "auth/logout";
    private static final String REFRESH_PATH = "auth/refresh";
    private static final String REGISTRATION_PATH="auth/register";
    private static final String UPDATE_EMAIL="auth/update-email";
    private static final String UPDATE_NAME="auth/update-username";
    private static final String CONTACTS_PATH = "contacts";
    private static final String JSON_MEDIA = "application/json";
    private static final OkHttpClient client = new OkHttpClient().newBuilder().build();


    public static String Authorisation(String login, String password){//при положительном ответе данные в состояние приложения на updateAuthState(String accessToken, String refreshToken)
        MediaType mediaType = MediaType.parse(JSON_MEDIA);
        RequestBody body = RequestBody.create(mediaType, "{\n  \"email\": \""+login+"\",\n  \"password\": \""+password+"\"\n}");
        Request request = new Request.Builder()
                .url(SERVER_URL + LOGIN_PATH)
                .post(body)
                .header("Content-Type", JSON_MEDIA)
                .header("Accept", JSON_MEDIA)
                .build();
        try (Response response = client.newCall(request).execute()){
            String responseBody = response.body().string();
            return responseBody;
        }catch (IOException e){
            return "Error auth";
        }
    }


//_____________________________Выход пользователя_______________________________
    public static String Logout() throws IOException {
        //запрос токена из состояния приложения
        MediaType mediaType = MediaType.parse(JSON_MEDIA);
        RequestBody body = RequestBody.create(mediaType, "{\n  \"refreshToken\": \"f47ac10b-58cc-4372-a567-0e02b2c3d479\"\n}");
        Request request = new Request.Builder()
                .url(SERVER_URL + LOGOUT_PATH)
                .method("POST", body)
                .addHeader("Content-Type", JSON_MEDIA)
                .addHeader("Accept", JSON_MEDIA)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        return responseBody;
    }
//_____________________________Обновление пользователя_______________________________
    public static String RefreshToken() throws IOException {
        MediaType mediaType = MediaType.parse(JSON_MEDIA);
        RequestBody body = RequestBody.create(mediaType, "{\n  \"refreshToken\": \"f47ac10b-58cc-4372-a567-0e02b2c3d479\"\n}");
        Request request = new Request.Builder()
                .url(SERVER_URL + REFRESH_PATH)
                .method("POST", body)
                .addHeader("Content-Type", JSON_MEDIA)
                .addHeader("Accept", JSON_MEDIA)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        return responseBody;
    }
//_____________________________Регистрация пользователя_______________________________Александру- добавлен логин в регистрацию
    public static String RegistrationUser(String email,String login,String password,String date) throws IOException {
        MediaType mediaType = MediaType.parse(JSON_MEDIA);
        RequestBody body = RequestBody.create(mediaType, "{\n  \"email\": \""+email+"\",\n  \"login\": \""+login+"\",\n  \"password\": \""+password+"\",\n  \"birthdate\": \""+date+"\"\n}");
        Request request = new Request.Builder()
                .url(SERVER_URL + REGISTRATION_PATH)
                .method("POST", body)
                .addHeader("Content-Type", JSON_MEDIA)
                .addHeader("Accept", JSON_MEDIA)
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        return responseBody;
    }

//_____________________________Обновление емейла_______________________________
public static String ChangeMail(String email,String password) throws IOException {
    MediaType mediaType = MediaType.parse(JSON_MEDIA);
    RequestBody body = RequestBody.create(mediaType, "{\n  \"email\": \""+email+"\",\n  \"password\": \""+password+"\"\n}");
    Request request = new Request.Builder()
            .url(SERVER_URL + UPDATE_EMAIL)
            .method("POST", body)
            .addHeader("Content-Type", JSON_MEDIA)
            .addHeader("Accept", JSON_MEDIA)
            .addHeader("Authorization", "••••••")
            .build();
    Response response = client.newCall(request).execute();
    String responseBody = response.body().string();
    return responseBody;
}
//_____________________________Обновление имени пользователя_______________________________
    public static String ChangeName(String username) throws IOException {
        MediaType mediaType = MediaType.parse(JSON_MEDIA);
        RequestBody body = RequestBody.create(mediaType, "{\n  \"username\": \""+username+"\"\n}");
        Request request = new Request.Builder()
                .url(SERVER_URL + UPDATE_NAME)
                .method("POST", body)
                .addHeader("Content-Type", JSON_MEDIA)
                .addHeader("Accept", JSON_MEDIA)
                .addHeader("Authorization", "••••••")
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        return responseBody;
    }
//_____________________________Получение сообщения_______________________________text/plain???
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
}