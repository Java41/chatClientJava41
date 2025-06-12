package org.example.chatclientjava41.UIComponents.Controller;

import org.example.chatclientjava41.Data.AllResponse;
import org.example.chatclientjava41.Data.ApplicationState;
import org.example.chatclientjava41.Main.SceneNavigator;
import org.example.chatclientjava41.UIComponents.View.ProfileElementCreator;
import org.example.chatclientjava41.UIComponents.View.MainMenuView;

public class MainMenuController {
    private static MainMenuView view;
    private SceneNavigator sceneNavigator;

    public void setView(MainMenuView view){
        this.view=view;
    }
    public void setSceneNavigator(SceneNavigator sceneNavigator){
        this.sceneNavigator=sceneNavigator;
    }
    public static void sendMessageField(String textMessage,long id){
        if(AllResponse.SendMessage(id, textMessage)){
            System.out.println("Сообщение отправлено");
        }
    }
    public void Logout(){
        view.setCurrentContact();
        AllResponse.Logout();
    }

    public void CreateContact(long id){
        if(AllResponse.AddContact(id)){
            view.createContactsPane();
        }
    }
    public void getProfileMainUser(){//добавить окно
        ProfileElementCreator.showProfileWindow(ApplicationState.getApplicationState());
    }
}
