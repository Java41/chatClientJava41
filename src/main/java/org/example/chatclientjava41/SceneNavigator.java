package org.example.chatclientjava41;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneNavigator extends Stage {
    private RestoreView restoreView;
    private DaniilView daniilView;
    private SeverView severView;
    private DefaultView defaultView;
    private Scene currentView;
    public SceneNavigator() {
        RestoreController restoreController=new RestoreController();
        DaniilController daniilController=new DaniilController();
        ViktorController viktorController=new ViktorController();
        DefaultController defaultController=new DefaultController();
        this.restoreView = new RestoreView(restoreController);
        this.daniilView = new DaniilView(daniilController);
        this.severView = new SeverView(viktorController);
        this.defaultView = new DefaultView(defaultController);
        restoreController.setView(restoreView);
        restoreController.setSceneNavigator(this);
        daniilController.setView(daniilView);
        daniilController.setSceneNavigator(this);
        viktorController.setView(severView);
        viktorController.setSceneNavigator(this);
        defaultController.setView(defaultView);
        defaultController.setSceneNavigator(this);
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
        currentView=defaultView.DefaultView();
        this.setTitle("Чат41");
    }
    public void setRegistration(){
        currentView=severView.SeverScene();
        this.setTitle("Регистрация");
    }
}
