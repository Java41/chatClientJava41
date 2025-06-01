package org.example.chatclientjava41;
import io.jsonwebtoken.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class ApplicationState {

    private static ApplicationState applicationState;
    private final SceneNavigator sceneNavigator=new SceneNavigator();
    private JwtParser jwtParser;
    private CurrentUser currentUser;
    private String accessToken;
    private String refreshToken;
    private static volatile boolean isAuthenticated = false;
    private long tokenExpirationTime;//время до обновления токена
    private static volatile boolean isTokenRefreshInProgress = false;//защита от множественных запросов на обновление токена
    private boolean isInitialAuthCheckDone = false;//Индикатор того, что приложение проверило наличие сохранённых токенов при старте
    private final Timer refreshTokenRequest= new Timer();
    //_____________________________________пользователь________________
    private long id;
    private String role;
    private String birthdate;
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private Map<Integer, Map<Date,String>> chats=new HashMap<>();


    public SceneNavigator getSceneNavigator() {
        return sceneNavigator;
    }

    public static ApplicationState getApplicationState(){
        if(applicationState==null){applicationState=new ApplicationState();}
        return applicationState;
    }

    public void responsePublicKey() {//получение публичного ключа который нужен для декодирования токенов
        try {
            byte[] decodedKey = Base64.getDecoder().decode(AllResponse.getPublicKey());
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedKey));
            this.jwtParser = Jwts.parser().verifyWith(publicKey).build();
        } catch (Exception e) {
        System.err.println("ОШИБКА при обработке публичного ключа: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        e.printStackTrace();
        }
    }

    public void updateAuthState(String []tokens) {
        //вылазящие при старте приложения ошибки невалидноси токенов изза стартовых токенов запуска-я от себя хрень туда вписал, пока не определимся как и где их храним пока приложение off
        Jws<Claims> claimsJws=null;
        try {
            claimsJws = jwtParser.parseClaimsJws(tokens[0]);
            this.accessToken = tokens[0];
            this.refreshToken = tokens[1];
            if(!isAuthenticated){
                this.role= claimsJws.getBody().get("groups").toString();
                this.birthdate= claimsJws.getBody().get("birthdate").toString();
                this.email= claimsJws.getBody().get("email").toString();
                this.username= claimsJws.getBody().get("username").toString();
                this.firstname= claimsJws.getBody().get("firstName").toString();
                this.lastname= claimsJws.getBody().get("lastName").toString();
            }
            this.tokenExpirationTime = (long)claimsJws.getBody().get("exp");

        } catch (ExpiredJwtException eje) {
            System.out.println("   ПРОВЕРКА НЕ ПРОШЛА: Просроченный токен (ExpiredJwtException) - " + eje.getMessage());
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            System.out.println("   ПРОВЕРКА НЕ ПРОШЛА или токен невалиден: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("   Неожиданная ошибка при проверке токена: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        if(accessToken!=null&&refreshToken!=null&&jwtParser!=null){
            if (!isAuthenticated){
                isAuthenticated=true;
                sceneNavigator.setMain();
            }
            refreshTokenRequest.scheduleAtFixedRate(_TimerTask(),1000,tokenExpirationTime-System.currentTimeMillis() / 1000);
        }else sceneNavigator.setAuth();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String [] refreshToken) {//в процессе
        refreshTokenRequest.cancel();
        if (refreshToken==null){
            LogOut();
        }else{
            isTokenRefreshInProgress=false;
        }
    }

    public void LogOut(){
        sceneNavigator.setAuth();
        accessToken=null;
        refreshToken=null;
        isAuthenticated=false;
        isTokenRefreshInProgress=false;
        tokenExpirationTime=0;
        refreshTokenRequest.cancel();
    }

    private TimerTask _TimerTask(){
        return new TimerTask() {
            @Override
            public void run() {
                while(isAuthenticated){
                    if(!isTokenRefreshInProgress){
                        if((System.currentTimeMillis() / 1000)+7000>tokenExpirationTime){
                            isTokenRefreshInProgress=true;
                        }else {
                            AllResponse.RefreshToken();
                        }
                    }
                }
            }
        };
    }
}
