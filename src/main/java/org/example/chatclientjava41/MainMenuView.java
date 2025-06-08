package org.example.chatclientjava41;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.util.List;
import java.util.Objects;

public class MainMenuView{
    private static MainMenuController mainMenuController;
    private VBox contactsList=new VBox();
    private VBox messagesContainer=new VBox();
    private BorderPane root;

    public MainMenuView(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public Scene MainScene(){
        root = new BorderPane();
        root.setLeft(createContactsPane());
        root.setRight(createProfilePane());
        root.setCenter(messagesContainer);
        Scene scene = new Scene(root, 1200, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/StyleMainMenu.css")).toExternalForm());
        return scene;
    }

    private VBox createContactsPane() {
        TextField fieldCreateContact=new TextField();
        fieldCreateContact.setPromptText("Введите id пользователя");
        Button createChatsBtn = new Button("Новый чат");
        createChatsBtn.setOnAction(actionEvent -> mainMenuController.CreateContact(Long.parseLong(fieldCreateContact.getText())));
        UserSearchComponent userSearch = new UserSearchComponent(); // Теперь загрузка внутри компонента
        VBox list=new VBox();
        List<Contact> contacts=ApplicationState.getApplicationState().getContacts();
        if(contacts!=null){
            for (int i = 0; i<contacts.size(); i++) {
                HBox contactItem = new HBox();
                Contact interlocutor=contacts.get(i);
                Circle avatarCircle = new Circle(20, Color.LIGHTGRAY);
                Label initialsLabel = new Label(interlocutor.getUserDTO().firstName());
                StackPane avatarStack = new StackPane(avatarCircle, initialsLabel);// Аватарка
                Label nameLabel = new Label(interlocutor.getUserDTO().firstName()+" " + interlocutor.getUserDTO().lastName());
                Label lastMsgLabel = new Label("Последнее сообщение...");
                contactItem.getChildren().addAll(avatarStack, new VBox(nameLabel, lastMsgLabel));
                contactItem.setOnMousePressed(e -> {
                    contactItem.setStyle("-fx-background-color: #cccccc;"); // Эффект нажатия
                    setMessagesContainer(interlocutor);
                });
                list.getChildren().add(contactItem);
            }
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(list);
        VBox contactsPane = new VBox(userSearch.getView(), fieldCreateContact,createChatsBtn, scrollPane);
        contactsPane.setPadding(new Insets(10));
        contactsPane.setSpacing(10);
        contactsPane.setPrefWidth(300);
        contactsPane.setStyle("-fx-background-color: #f0f0f0;");
        return contactsPane;
    }

    private VBox createProfilePane() {
        VBox profileVBox = new VBox();
        profileVBox.setPadding(new Insets(10));
        profileVBox.setSpacing(15);
        profileVBox.setPrefWidth(250);
        profileVBox.setStyle("-fx-background-color: #e0e0e0;");
        Circle avatarCircle = new Circle(40, Color.LIGHTGRAY);// Аватарка или первая буква имени
        Label initialsLabel = new Label(ApplicationState.getApplicationState().getFirstname().charAt(0)+"");
        initialsLabel.setFont(Font.font(24));
        StackPane avatarStack = new StackPane();
        avatarStack.getChildren().addAll(avatarCircle, initialsLabel);
        Label nameLabel = new Label(ApplicationState.getApplicationState().getFirstname()+" "+ApplicationState.getApplicationState().getLastname());
        nameLabel.setFont(Font.font(16));
        Label lastSeenLabel = new Label("Был в сети: 2 минуты назад");
        Button viewProfileBtn = new Button("Посмотреть профиль");
        viewProfileBtn.setOnAction(actionEvent ->mainMenuController.getProfileMainUser());
        Button findInChatBtn = new Button("Найти в чате");
        Button sendImageBtn = new Button("Отправленные изображение");
        Button moreInfoBtn = new Button("Больше информации");
        Button logout=new Button("Выйти из аккаунта");
        viewProfileBtn.setMaxWidth(Double.MAX_VALUE);
        findInChatBtn.setMaxWidth(Double.MAX_VALUE);
        sendImageBtn.setMaxWidth(Double.MAX_VALUE);
        moreInfoBtn.setMaxWidth(Double.MAX_VALUE);
        logout.setMaxWidth(Double.MAX_VALUE);
        logout.setOnAction(actionEvent -> mainMenuController.Logout());
        VBox buttonsBox = new VBox(viewProfileBtn,findInChatBtn,sendImageBtn,moreInfoBtn,logout);// Кнопки профиля
        profileVBox.getChildren().addAll(avatarStack,nameLabel,lastSeenLabel,buttonsBox);
        return profileVBox;
    }
    public void setMessagesContainer(Contact contact){
        messagesContainer= mainMenuController.CurrentChat(contact);
        root.setCenter(messagesContainer);
    }

    public void setContactsList() {
        this.contactsList = createContactsPane();
        root.setCenter(contactsList);
    }
}