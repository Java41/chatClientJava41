package org.example.chatclientjava41;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class MainMenuView{
    private static MainMenuController mainMenuController;
    private VBox contactsList=new VBox();
    private VBox messagesContainer=new VBox();

    public MainMenuView(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public Scene MainScene(){
        mainMenuController.ContactList();
        BorderPane root = new BorderPane();
        VBox contactsBox =createContactsPane() ;// 1. Панель контактов слева
        VBox profileBox = createProfilePane();// 2. Панель профиля справа
        ScrollPane chat=new ScrollPane();// 3. Центр - окно чата
        chat.setContent(messagesContainer);
        root.setLeft(contactsBox);
        root.setRight(profileBox);
        root.setCenter(chat);
        Scene scene = new Scene(root, 1200, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/StyleMainMenu.css")).toExternalForm());
        return scene;
    }

    private VBox createContactsPane() {
        VBox contactsVBox = new VBox();
        TextField fieldCreateContact=new TextField();
        fieldCreateContact.setPromptText("Введите id пользователя");
        Button createChatsBtn = new Button("Новый чат");
        createChatsBtn.setOnAction(actionEvent -> mainMenuController.CreateContact(Long.parseLong(fieldCreateContact.getText())));
        UserSearchComponent userSearch = new UserSearchComponent(); // Теперь загрузка внутри компонента
        ScrollPane scrollPane = new ScrollPane(contactsList);
        contactsVBox.getChildren().addAll(userSearch.getView(), fieldCreateContact,createChatsBtn, scrollPane);
        return contactsVBox;
    }

    private VBox createProfilePane() {
        VBox profileVBox = new VBox();
        Circle avatarCircle = new Circle();// Аватарка или первая буква имени
        Label initialsLabel = new Label("И");
        StackPane avatarStack = new StackPane();
        avatarStack.getChildren().addAll(avatarCircle, initialsLabel);
        Label nameLabel = new Label(ApplicationState.getApplicationState().getFirstname()+" "+ApplicationState.getApplicationState().getLastname());
        Label lastSeenLabel = new Label("Был в сети: 2 минуты назад");
        Button viewProfileBtn = new Button("Посмотреть профиль");
        viewProfileBtn.setOnAction(actionEvent ->mainMenuController.getProfileMainUser());
        Button findInChatBtn = new Button("Найти в чате");
        Button sendImageBtn = new Button("Отправленные изображение");
        Button moreInfoBtn = new Button("Больше информации");
        Button logout=new Button("Выйти из аккаунта");
        logout.setOnAction(actionEvent -> mainMenuController.Logout());
        VBox buttonsBox = new VBox(viewProfileBtn,findInChatBtn,sendImageBtn,moreInfoBtn,logout);// Кнопки профиля
        profileVBox.getChildren().addAll(avatarStack,nameLabel,lastSeenLabel,buttonsBox);
        return profileVBox;
    }

    public void setMessagesContainer(VBox messagesContainer) {
        this.messagesContainer = messagesContainer;
    }

    public void setContactsList(VBox contactsList) {
        this.contactsList = contactsList;
    }
}