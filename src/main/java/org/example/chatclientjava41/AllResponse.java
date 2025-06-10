package org.example.chatclientjava41;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.example.chatclientjava41.dto.MessageDTO;
import org.example.chatclientjava41.dto.UserDTO;

import java.io.IOException;
import java.util.List;

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
    private static final String USER_PATH = "/users";
    private static final OkHttpClient client = new OkHttpClient().newBuilder().build();

    private static abstract class ResponseTemplate<T> {
        protected abstract Request buildRequest();
        protected abstract T parseSuccessfulResponse(String responseBody) throws IOException;
        protected T handleError(int statusCode, String responseBody) {
            return null;
        }

        public final T execute() {
            Request request = buildRequest();
            try (Response response = client.newCall(request).execute()) {
                String responseBody = response.body() != null ? response.body().string() : "";
                return response.isSuccessful() ? parseSuccessfulResponse(responseBody) : handleError(response.code(), responseBody);
            } catch (IOException ioException) {
                return handleError(-1, ioException.getMessage());
            }
        }
    }

    public static String getPublicKey() {
        return new ResponseTemplate<String>() {
            protected Request buildRequest() {
                return new Request.Builder()
                        .url(SERVER_URL + PUB_KEY_PATH)
                        .get()
                        .build();
            }
            protected String parseSuccessfulResponse(String responseBody) {
                if (responseBody.contains("-----BEGIN PUBLIC KEY-----") && responseBody.contains("-----END PUBLIC KEY-----")) {
                    return responseBody
                            .replace("-----BEGIN PUBLIC KEY-----", "")
                            .replace("-----END PUBLIC KEY-----", "")
                            .replaceAll("\\s", "");
                }
                return "Публичный ключ не был получен";
            }
            protected String handleError(int statusCode, String responseBody) {
                return statusCode == -1 ? "Ошибка" : "Публичный ключ не был получен";
            }
        }.execute();
    }

    public static String Authorisation(String email, String password) {
        return new ResponseTemplate<String>() {
            protected Request buildRequest() {
                MediaType mediaType = MediaType.parse(JSON_MEDIA);
                RequestBody requestBody = RequestBody.create(mediaType, "{ \"email\":\"" + email + "\", \"password\":\"" + password + "\" }");
                return new Request.Builder()
                        .url(SERVER_URL + LOGIN_PATH)
                        .post(requestBody)
                        .header("Content-Type", JSON_MEDIA)
                        .header("Accept", JSON_MEDIA)
                        .build();
            }
            protected String parseSuccessfulResponse(String responseBody) {
                if (responseBody.contains("\"accessToken\":\"") && responseBody.contains("\",\"refreshToken\":\"")) {
                    String cleanedTokens = responseBody.replaceAll("[{}\" ]", "")
                            .replace("accessToken:", "");
                    String[] tokens = cleanedTokens.split(",refreshToken:");
                    applicationState.updateAuthState(tokens);
                    return "Вход успешно состоялся";
                }
                return "Неверный логин или пароль";
            }
            protected String handleError(int statusCode, String responseBody) {
                return "Error auth";
            }
        }.execute();
    }

    public static void Logout() {
        new ResponseTemplate<Void>() {
            protected Request buildRequest() {
                MediaType mediaType = MediaType.parse(JSON_MEDIA);
                RequestBody requestBody = RequestBody.create(mediaType, "{ \"refreshToken\":\"" + applicationState.getRefreshToken() + "\" }");
                return new Request.Builder()
                        .url(SERVER_URL + LOGOUT_PATH)
                        .post(requestBody)
                        .header("Content-Type", JSON_MEDIA)
                        .header("Accept", JSON_MEDIA)
                        .build();
            }
            protected Void parseSuccessfulResponse(String responseBody) {
                applicationState.LogOut();
                return null;
            }
            protected Void handleError(int statusCode, String responseBody) {
                System.out.println(statusCode == -1 ? responseBody : "чет запрос не сработал");
                return null;
            }
        }.execute();
    }

    public static void RefreshToken() {
        new ResponseTemplate<Void>() {
            protected Request buildRequest() {
                MediaType mediaType = MediaType.parse(JSON_MEDIA);
                RequestBody requestBody = RequestBody.create(mediaType, "{ \"refreshToken\":\"" + applicationState.getRefreshToken() + "\" }");
                return new Request.Builder()
                        .url(SERVER_URL + REFRESH_PATH)
                        .post(requestBody)
                        .header("Content-Type", JSON_MEDIA)
                        .header("Accept", JSON_MEDIA)
                        .build();
            }
            protected Void parseSuccessfulResponse(String responseBody) throws IOException {
                JsonNode json = new ObjectMapper().readTree(responseBody);
                applicationState.updateAuthState(new String[]{json.get("accessToken").asText(), json.get("refreshToken").asText()});
                return null;
            }
            protected Void handleError(int statusCode, String responseBody) {
                if (statusCode == -1){
                    System.out.println("Ошибка получения токена");
                }
                else if (statusCode == 400) {
                    System.out.println("400 " + responseBody);
                }
                else if (statusCode == 401) {
                    applicationState.updateAuthState(null);
                }
                return null;
            }
        }.execute();
    }

    public static String RegistrationUser(String email, String password, String birthdate, String firstName, String lastName) {
        return new ResponseTemplate<String>() {
            protected Request buildRequest() {
                MediaType mediaType = MediaType.parse(JSON_MEDIA);
                RequestBody requestBody = RequestBody.create(
                        mediaType,
                        "{ \"email\":\"" + email + "\", \"password\":\"" + password + "\", "
                                + "\"birthdate\":\"" + birthdate + "\", "
                                + "\"firstName\":\"" + firstName + "\", "
                                + "\"lastName\":\"" + lastName + "\" }");
                return new Request.Builder()
                        .url(SERVER_URL + REGISTRATION_PATH)
                        .post(requestBody)
                        .header("Content-Type", JSON_MEDIA)
                        .header("Accept", JSON_MEDIA)
                        .build();
            }
            protected String parseSuccessfulResponse(String responseBody) throws IOException {
                JsonNode json = new ObjectMapper().readTree(responseBody);
                if (json.has("accessToken") && json.has("refreshToken")) {
                    applicationState.updateAuthState(new String[]{json.get("accessToken").asText(), json.get("refreshToken").asText()});
                    return "Регистрация прошла успешна";
                }
                return "Ошибка регистрации: некорректный ответ";
            }
            protected String handleError(int statusCode, String responseBody) {
                return statusCode == -1 ? "error registration" : "Ошибка регистрации: " + statusCode;
            }
        }.execute();
    }

    public static void updateProfileFieldsAPI(String jsonData) {
        new ResponseTemplate<Void>() {
            protected Request buildRequest() {
                RequestBody requestBody = RequestBody.create(jsonData, MediaType.parse(JSON_MEDIA));
                return new Request.Builder()
                        .url(SERVER_URL + "/profile")
                        .patch(requestBody)
                        .header("Content-Type", JSON_MEDIA)
                        .header("Accept", JSON_MEDIA)
                        .header("Authorization", "Bearer " + applicationState.getAccessToken())
                        .build();
            }
            protected Void parseSuccessfulResponse(String responseBody) {
                if (responseBody.contains("\"accessToken\":\"") && responseBody.contains("\",\"refreshToken\":\"")) {
                    String cleanedTokens = responseBody.replaceAll("[{}\" ]", "")
                            .replace("accessToken:", "");
                    String[] tokens = cleanedTokens.split(",refreshToken:");
                    applicationState.updateAuthState(tokens);
                    System.out.println("Попытка обновить поле удалась так как ответ содержит токен");
                } else {
                    System.out.println(responseBody);
                }
                return null;
            }
            protected Void handleError(int statusCode, String responseBody) {
                System.out.println("Ошибка обновления профиля: " + (statusCode == -1 ? responseBody : statusCode + " " + responseBody));
                return null;
            }
        }.execute();
    }

    public static boolean SendMessage(Long recipientId, String messageText) {
        return new ResponseTemplate<Boolean>() {
            protected Request buildRequest() {
                MediaType mediaType = MediaType.parse(JSON_MEDIA);
                RequestBody requestBody = RequestBody.create(mediaType, "{ \"recipientId\":" + recipientId + ", \"content\":\"" + messageText + "\" }");

                return new Request.Builder()
                        .url(SERVER_URL + SEND_MESSAGE)
                        .post(requestBody)
                        .header("Content-Type", JSON_MEDIA)
                        .header("Accept", JSON_MEDIA)
                        .header("Authorization", "Bearer " + applicationState.getAccessToken())
                        .build();
            }
            protected Boolean parseSuccessfulResponse(String responseBody) {
                applicationState.updateAllMessages();
                return true;
            }
            protected Boolean handleError(int statusCode, String responseBody) {
                if (statusCode == -1){
                    System.out.println("Error auth");
                }
                return false;
            }
        }.execute();
    }

    public static List<MessageDTO> GetMessage(Long id) {
        return new ResponseTemplate<List<MessageDTO>>() {
            protected Request buildRequest() {
                return new Request.Builder()
                        .url(SERVER_URL + SEND_MESSAGE+"?since="+applicationState.getLastTimeResponseMassage()+"&with="+id)
                        .get()
                        .header("Accept", "application/json")
                        .header("Authorization", "Bearer " + applicationState.getAccessToken())
                        .build();
            }
            protected List<MessageDTO> parseSuccessfulResponse(String responseBody) throws IOException {
                return new ObjectMapper().readValue(responseBody, new TypeReference<>() {});
            }
            protected List<MessageDTO> handleError(int statusCode, String responseBody) {
                System.out.println(statusCode == -1 ? "Error auth" : "Messages got it");
                return List.of();
            }
        }.execute();
    }

    public static List<UserDTO> getAllContacts() {
        return new ResponseTemplate<List<UserDTO>>() {
            protected Request buildRequest() {
                return new Request.Builder()
                        .url(SERVER_URL + CONTACTS_PATH)
                        .get()
                        .header("Accept", "application/json")
                        .header("Authorization", "Bearer " + applicationState.getAccessToken())
                        .build();
            }
            protected List<UserDTO> parseSuccessfulResponse(String responseBody) throws IOException {
                return new ObjectMapper().readValue(responseBody, new TypeReference<>() {});
            }
            protected List<UserDTO> handleError(int statusCode, String responseBody) {
                if (statusCode == -1){
                    System.out.println("Error auth");
                }
                else {
                    System.out.println("Users got it");
                }
                return List.of();
            }
        }.execute();
    }

    public static boolean AddContact(Long userId) {
        return new ResponseTemplate<Boolean>() {
            protected Request buildRequest() {
                MediaType mediaType = MediaType.parse(JSON_MEDIA);
                RequestBody requestBody = RequestBody.create(mediaType, "{ \"id\":" + userId + " }");
                return new Request.Builder()
                        .url(SERVER_URL + CONTACTS_PATH)
                        .post(requestBody)
                        .header("Authorization", "Bearer " + applicationState.getAccessToken())
                        .header("Accept", JSON_MEDIA)
                        .build();
            }
            protected Boolean parseSuccessfulResponse(String responseBody) throws IOException {
                applicationState.addContact(new ObjectMapper()
                        .readValue(responseBody, new TypeReference<>() {}));
                System.out.println("contact add");
                return true;
            }
            protected Boolean handleError(int statusCode, String responseBody) {
                if (statusCode == -1){
                    System.out.println("Error auth");
                }
                else if (statusCode == 404 || statusCode == 400){
                    System.out.println("Такого пользователя не существует");
                }
                else if (statusCode == 409){
                    System.out.println("Переключение на уже существующий контакт");
                }
                else{
                    System.out.println(responseBody);
                }
                return false;
            }
        }.execute();
    }

    public static List<UserDTO> getUsers() {
        return new ResponseTemplate<List<UserDTO>>() {

            protected Request buildRequest() {
                return new Request.Builder()
                        .url(SERVER_URL + USER_PATH)
                        .get()
                        .header("Accept", "application/json")
                        .header("Authorization", "Bearer " + applicationState.getAccessToken())
                        .build();
            }
            protected List<UserDTO> parseSuccessfulResponse(String responseBody) throws IOException {
                return new ObjectMapper().readValue(responseBody, new TypeReference<>() {});
            }
            protected List<UserDTO> handleError(int statusCode, String responseBody) {
                System.out.println("Users got it");

                return List.of();
            }
        }.execute();
    }
}