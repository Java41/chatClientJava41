package org.example.chatclientjava41;

import java.util.LinkedHashMap;

public class ApplicationState {

    private static ApplicationState applicationState;
    private SceneNavigator sceneNavigator;
    private String accessToken;
    private String refreshToken;
    private boolean isAuthenticated = false;
    private long tokenExpirationTime;//время до обновления токена
    private boolean isTokenRefreshInProgress = false;//защита от множественных запросов на обновление токена
    private boolean isInitialAuthCheckDone = false;//Индикатор того, что приложение проверило наличие сохранённых токенов при старте
    public static ApplicationState getApplicationState(){
        if(applicationState==null){applicationState=new ApplicationState();}
        return applicationState;
    }

    public SceneNavigator getSceneNavigator() {
        return sceneNavigator;
    }

    public void updateAuthState(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        //дешифровка токенов с сборкой текущего юзера
        CurrentUser currentUser=new CurrentUser("Злой грызь","@0000000001",null,new LinkedHashMap<>(),"Душу_гуся:DDD","+1234567890");//по идее сюда дешифрованные данные
//        this.isAuthenticated = true;
//        this.tokenExpirationTime = System.currentTimeMillis() + expiresIn * 1000;
//        this.lastAuthError = null;
// if(isAuthenticated)?sceneNavigator.setMain():sceneNavigator.setAuth();//если подгрузились данные гдето у нас хранящиеся, то вкл Мейн окно, если нет-авторизация
       this.sceneNavigator=new SceneNavigator();
       sceneNavigator.setRegistration();
    }
    //_____________________________Обновление токена_______________________________
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
}
