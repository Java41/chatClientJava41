package org.example.chatclientjava41;
import io.jsonwebtoken.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApplicationState {

    private static ApplicationState applicationState;
    private final SceneNavigator sceneNavigator=new SceneNavigator();
    private JwtParser jwtParser;
    private String accessToken;
    private String refreshToken;
    private static volatile boolean isAuthenticated = false;
    private long tokenExpirationTime;//время до обновления токена
    private static volatile boolean isTokenRefreshInProgress = false;//защита от множественных запросов на обновление токена
    private boolean isInitialAuthCheckDone = false;//Индикатор того, что приложение проверило наличие сохранённых токенов при старте
    private Thread refreshTokenResponse;
    //_____________________________________пользователь________________
    private String role;
    private String birthdate;
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private Map<Integer, Map<Date,String>> chats=new HashMap<>();

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
    public SceneNavigator getSceneNavigator() {
        return sceneNavigator;
    }

    public void updateAuthState(String []tokens) {
        //вылазящие при старте приложения ошибки невалидноси токенов изза стартовых токенов запуска-я от себя хрень туда вписал, пока не определимся как и где их храним пока приложение off
        Jws<Claims> claimsJws=null;
        for (int i=0;i<2;i++){
           try {
               claimsJws = jwtParser.parseClaimsJws(tokens[i]);

           } catch (ExpiredJwtException eje) {
               System.out.println("   ПРОВЕРКА НЕ ПРОШЛА: Просроченный токен (ExpiredJwtException) - " + eje.getMessage());
           } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
               System.out.println("   ПРОВЕРКА НЕ ПРОШЛА или токен невалиден: " + e.getClass().getSimpleName() + " - " + e.getMessage());
           } catch (Exception e) { // Более общий перехватчик для неожиданных ошибок парсинга
               System.out.println("   Неожиданная ошибка при проверке токена: " + e.getClass().getSimpleName() + " - " + e.getMessage());
           }
            if(i==0&&jwtParser.isSigned(tokens[i])){
                this.accessToken = tokens[i];
                try {this.role= claimsJws.getBody().get("groups").toString();
                    this.birthdate= claimsJws.getBody().get("birthdate").toString();
                    this.email= claimsJws.getBody().get("email").toString();
                    this.username= claimsJws.getBody().get("username").toString();
                    this.firstname= claimsJws.getBody().get("firstName").toString();
                    this.lastname= claimsJws.getBody().get("lastName").toString();
                    this.tokenExpirationTime = (long)claimsJws.getBody().get("exp");
                    //String[] parts = tokens[i].split("[.]");//другой способ дешифровки-без claims
                    //String header = new String(Base64.getUrlDecoder().decode(parts[0]));
                    //String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
                    //___________полная выписка из дешифровки токена______________________
//                    StringBuilder claimsStr = new StringBuilder("   ПРОВЕРЕННЫЕ УТВЕРЖДЕНИЯ (CLAIMS) из полезной нагрузки:\n");
//                    claimsJws.getBody().forEach((k, v) -> claimsStr.append(String.format("       %s: %s\n", k, v)));
//                    System.out.println(claimsStr.toString());
                } catch (Exception e) { // Ошибка декодирования Base64 или другая
                    System.out.println("   Ошибка при декодировании или отображении частей токена: " + e.getMessage());
                }
            }else {
                this.refreshToken = tokens[i];
            }
       }
        if(accessToken!=null&&refreshToken!=null&&jwtParser!=null){
            isAuthenticated=true;
            sceneNavigator.setMain();
            refreshTokenResponse=new Thread(()->{
                while(isAuthenticated){
                    if(!isTokenRefreshInProgress){
                        if((System.currentTimeMillis() / 1000)+7000>tokenExpirationTime){
                            isTokenRefreshInProgress=true;
                        }else {
                            AllResponse.RefreshToken();
                        }
                    }

                    try{Thread.sleep(500);}
                    catch (InterruptedException e){
                        System.out.println(e.getMessage());
                    }
                }
            });
            refreshTokenResponse.start();
        }else sceneNavigator.setAuth();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        if (refreshToken==null){
            refreshTokenResponse.interrupt();
            isAuthenticated=false;
            sceneNavigator.setAuth();
        }else{
            this.refreshToken = refreshToken;
            isTokenRefreshInProgress=false;//дописать извлечение срока годности из токена
        }
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
