package org.example.chatclientjava41;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneNavigator extends Stage {
    private RestoreView restoreView;
    private DaniilView daniilView;
    private SeverView severView;
    private MainView mainView;
    private Scene currentView;
    public SceneNavigator() {
        RestoreController restoreController=new RestoreController();
        DaniilController daniilController=new DaniilController();
        ViktorController viktorController=new ViktorController();
        MainController mainController=new MainController();
        this.restoreView = new RestoreView(restoreController);
        this.daniilView = new DaniilView(daniilController);
        this.severView = new SeverView(viktorController);
        this.mainView = new MainView(mainController);
        restoreController.setView(restoreView);
        restoreController.setSceneNavigator(this);
        daniilController.setView(daniilView);
        daniilController.setSceneNavigator(this);
        viktorController.setView(severView);
        viktorController.setSceneNavigator(this);
        mainController.setView(mainView);
        mainController.setSceneNavigator(this);
        this.currentView=severView.SeverScene();
        this.setScene(currentView);
        this.setTitle("Авторизация");
        this.show();
    }
    public void setAuth(){
        currentView=daniilView.DaniilScene();
        this.setTitle("Авторизация");
    }
    public void setRestore(){
        currentView=restoreView.RestoreScene();
        this.setTitle("Восстановление пароля");
    }
    public void setDefault(){
        currentView=mainView.MainScene();
        this.setTitle("Чат41");
    }
    public void setRegistration(){
        currentView=severView.SeverScene();
        this.setTitle("Регистрация");
    }
}
