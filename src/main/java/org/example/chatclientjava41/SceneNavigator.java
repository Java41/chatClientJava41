package org.example.chatclientjava41;

public class SceneNavigator {
    private final ServerView view;

    public SceneNavigator(ServerView view){
        this.view = view;
    }

    public void setAuth(){
        view.setAuth();
    }
}
