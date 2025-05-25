package org.example.chatclientjava41;

import java.util.LinkedHashMap;

public class ApplicationState {
    private static final ApplicationState INSTANCE = new ApplicationState();
    private ApplicationState() {
        if (INSTANCE != null) {
            throw new RuntimeException("Используйте getInstance() для получения экземпляра");
        }
    }

    // Метод для получения единственного экземпляра
    public static ApplicationState getInstance() {
        return INSTANCE;
    }

    private LinkedHashMap<String, String> chats = new LinkedHashMap<>();
    private boolean isAuthenticated = false;
    private long tokenExpirationTime;
    private boolean isTokenRefreshInProgress = false;
    private boolean isInitialAuthCheckDone = false;
    private String lastAuthError;

    public void updateAuthState(
            String accessToken,
            String refreshToken,
            LinkedHashMap<String, String> chats,
            long expiresIn
    ) {
        String username = null;
        this.chats = chats != null ? new LinkedHashMap<>(chats) : new LinkedHashMap<>();
        this.isAuthenticated = true;
        this.tokenExpirationTime = System.currentTimeMillis() + expiresIn * 1000;
        this.lastAuthError = null;
    }
}
