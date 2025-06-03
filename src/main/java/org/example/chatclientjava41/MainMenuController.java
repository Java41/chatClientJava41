package org.example.chatclientjava41;
import javafx.scene.layout.VBox;

public class MainMenuController {
    private MainMenuView view;
    private SceneNavigator sceneNavigator;

    public void setView(MainMenuView view){
        this.view=view;
    }
    public void setSceneNavigator(SceneNavigator sceneNavigator){
        this.sceneNavigator=sceneNavigator;
    }
    public static void sendMessageField(String textMessage){
        System.out.println(AllResponse.SendMessage(1,textMessage));
    }
    public VBox getListChats(){
        VBox contactList=new VBox();

        return contactList;
    }
    public void getProfileMainUser(){//добавить окно
        ProfileElementCreator.showProfileWindow(ApplicationState.getApplicationState());
    }
}
