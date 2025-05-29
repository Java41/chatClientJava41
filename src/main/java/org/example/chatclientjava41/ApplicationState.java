package org.example.chatclientjava41;
import io.jsonwebtoken.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.security.SignatureException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ApplicationState {

    private static ApplicationState applicationState;
    private final SceneNavigator sceneNavigator=new SceneNavigator();
    private JwtParser jwtParser;
    private CurrentUser currentUser;
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
            if(i==0){
                this.accessToken = tokens[i];
                try {this.currentUser=new CurrentUser(claimsJws.getBody().get("groups").toString(),
                        claimsJws.getBody().get("birthdate").toString(),
                        claimsJws.getBody().get("email").toString(),
                        claimsJws.getBody().get("username").toString(),
                        claimsJws.getBody().get("firstName").toString(),
                        claimsJws.getBody().get("lastName").toString());
                    this.tokenExpirationTime = (long)claimsJws.getBody().get("exp");
                    //String[] parts = tokens[i].split("[.]");//другой способ дешифровки-без claims
                    //String header = new String(Base64.getUrlDecoder().decode(parts[0]));
                    //String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
                    //___________полная выписка из дешифровки токена______________________
                    StringBuilder claimsStr = new StringBuilder("   ПРОВЕРЕННЫЕ УТВЕРЖДЕНИЯ (CLAIMS) из полезной нагрузки:\n");
                    claimsJws.getBody().forEach((k, v) -> claimsStr.append(String.format("       %s: %s\n", k, v)));
                    System.out.println(claimsStr.toString());
                } catch (Exception e) { // Ошибка декодирования Base64 или другая
                    System.out.println("   Ошибка при декодировании или отображении частей токена: " + e.getMessage());
                }
            }else {
                this.refreshToken = tokens[i];
            }
       }
        if(accessToken!=null&&refreshToken!=null&&jwtParser!=null){
            this.isAuthenticated=true;
            sceneNavigator.setRegistration();
        }else sceneNavigator.setAuth();
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
