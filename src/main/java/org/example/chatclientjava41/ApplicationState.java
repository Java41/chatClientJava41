package org.example.chatclientjava41;

import java.util.LinkedHashMap;

public class ApplicationState {
    private String accessToken;
    private String refreshToken;
    private String username;
    private LinkedHashMap<String,String> chats = new LinkedHashMap<>();
    private boolean isAuthenticated = false;
    private long tokenExpirationTime;
    private boolean isTokenRefreshInProgress = false;
    private boolean isInitialAuthCheckDone = false;
    private String lastAuthError;

    public void updateAuthState(String accessToken, String refreshToken,
                                String username, LinkedHashMap<String,String> chats,
                                long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.chats = chats != null ? new LinkedHashMap<>(chats) : new LinkedHashMap<>();
        this.isAuthenticated = true;
        this.tokenExpirationTime = System.currentTimeMillis() + expiresIn * 1000;
        this.lastAuthError = null;
    }
}
