package org.example.chatclientjava41;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Objects;

public class MainMenuView{
    private static MainMenuController mainMenuController;
    private VBox contactsList=new VBox();
    private VBox messagesContainer=new VBox();

    public MainMenuView(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }
    public Scene MainScene(){
        // Основной корень - BorderPane
        BorderPane root = new BorderPane();

        // 1. Панель контактов слева
        VBox contactsBox = createContactsPane();

        // 2. Панель профиля справа
        VBox profileBox = createProfilePane();

        // 3. Центр - окно чата
        VBox chatBox = createChatPane();
        root.setLeft(contactsBox);
        root.setRight(profileBox);
        root.setCenter(chatBox);
        Scene scene = new Scene(root, 1500, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/StyleRestoreMenu.css")).toExternalForm());
        return scene;
    }

    private VBox createContactsPane() {
        VBox contactsVBox = new VBox();
        contactsVBox.setPadding(new Insets(10));
        contactsVBox.setSpacing(10);
        contactsVBox.setPrefWidth(300);

        contactsVBox.setStyle("-fx-background-color: #f0f0f0;");
        // Кнопка "найти чаты"
        TextField fieldCreateContact=new TextField();
        fieldCreateContact.setPromptText("Введите id пользователя");
        Button createChatsBtn = new Button("Новый чат");
        createChatsBtn.setOnAction(actionEvent -> mainMenuController.CreateContact(fieldCreateContact.getText()));

//         UserSearchComponent userSearch = new UserSearchComponent(); // Теперь загрузка внутри компонента
//         userSearch.setOnUserClicked(System.out::println);


        createChatsBtn.setMaxWidth(Double.MAX_VALUE);
        contactsList=mainMenuController.ContactList();
        contactsList.setSpacing(5);
        ScrollPane scrollPane = new ScrollPane(contactsList);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);


        contactsVBox.getChildren().addAll( fieldCreateContact,createChatsBtn, scrollPane);

        //contactsVBox.getChildren().addAll( userSearch.getView(), scrollPane);


        return contactsVBox;
    }

    private VBox createProfilePane() {
        VBox profileVBox = new VBox();
        profileVBox.setPadding(new Insets(10));
        profileVBox.setSpacing(15);
        profileVBox.setPrefWidth(250);
        profileVBox.setStyle("-fx-background-color: #e0e0e0;");

        // Аватарка или первая буква имени
        Circle avatarCircle = new Circle(40, Color.LIGHTGRAY);
        Label initialsLabel = new Label("И");
        initialsLabel.setFont(Font.font(24));

        StackPane avatarStack = new StackPane();
        avatarStack.getChildren().addAll(avatarCircle, initialsLabel);

        // Имя пользователя
        Label nameLabel = new Label(ApplicationState.getApplicationState().getFirstname()+" "+ApplicationState.getApplicationState().getLastname());
        nameLabel.setFont(Font.font(16));

        // Последний раз в сети
        Label lastSeenLabel = new Label("Был в сети: 2 минуты назад");
        lastSeenLabel.setFont(Font.font(12));
        lastSeenLabel.setTextFill(Color.GRAY);

        // Кнопки профиля
        VBox buttonsBox = getVBox();

        profileVBox.getChildren().addAll(
                avatarStack,
                nameLabel,
                lastSeenLabel,
                buttonsBox
        );

        return profileVBox;
    }

    private static VBox getVBox() {
        Button viewProfileBtn = new Button("Посмотреть профиль");
        viewProfileBtn.setOnAction(actionEvent ->mainMenuController.getProfileMainUser());
        Button findInChatBtn = new Button("Найти в чате");
        Button sendImageBtn = new Button("Отправленные изображение");
        Button moreInfoBtn = new Button("Больше информации");
        Button logout=new Button("Выйти из аккаунта");
        logout.setOnAction(actionEvent -> mainMenuController.Logout());
        viewProfileBtn.setMaxWidth(Double.MAX_VALUE);
        findInChatBtn.setMaxWidth(Double.MAX_VALUE);
        sendImageBtn.setMaxWidth(Double.MAX_VALUE);
        moreInfoBtn.setMaxWidth(Double.MAX_VALUE);
        logout.setMaxWidth(Double.MAX_VALUE);

        return new VBox(5, viewProfileBtn, findInChatBtn, sendImageBtn, moreInfoBtn,logout);
    }

    private VBox createChatPane() {
        VBox chatVBox= new VBox();
        chatVBox.setPadding(new Insets(10));
        chatVBox.setSpacing(10);

        // Верхняя панель с именем контакта и кнопками звонка/видео
        HBox chatHeader= new HBox();
        chatHeader.setAlignment(Pos.CENTER_LEFT);

        // Аватарка контакта
        Circle avatarCircle= new Circle(20, Color.LIGHTGRAY);
        Label initialsLabel= new Label("К");
        initialsLabel.setFont(Font.font(14));

        StackPane avatarStack= new StackPane();
        avatarStack.getChildren().addAll(avatarCircle, initialsLabel);

        // Имя и время активности
        Label nameLbl= new Label("Контакт");
        nameLbl.setFont(Font.font(14));

        Label timeLbl= new Label("Последняя активность: 12:45");
        timeLbl.setFont(Font.font(12));

        VBox nameTimeBox= new VBox(nameLbl,timeLbl);

        HBox headerLeftPart= new HBox(10, avatarStack, nameTimeBox);

        Region spacerHeaderRight= new Region();
        HBox.setHgrow(spacerHeaderRight, Priority.ALWAYS);

        // Кнопки звонка и видео звонка
        Button callBtn= new Button("Звонок");
        Button videoCallBtn= new Button("Видео звонок");

        HBox headerButtonsBox= new HBox(5, callBtn, videoCallBtn);

        HBox headerTopRow= new HBox(headerLeftPart, spacerHeaderRight, headerButtonsBox);


        // Окно сообщений (список сообщений)
        messagesContainer.setSpacing(10);
        messagesContainer.prefHeightProperty().bind(chatVBox.heightProperty());

// Scroll pane для сообщений
        VBox chatContent = getVBox(messagesContainer, headerTopRow);

// Сделать центр - это весь чат с фиксированной шириной или растягивающийся по ширине.
        chatContent.prefWidthProperty().bind(chatContent.widthProperty());
        chatVBox.getChildren().add(chatContent);
        return chatVBox;
    }

    private VBox getVBox(VBox messagesContainer, HBox headerTopRow) {
        ScrollPane messagesScroll= new ScrollPane(messagesContainer);
        messagesScroll.setFitToWidth(true);

// Ввод сообщения и кнопки справа от него
        TextField messageInput= new TextField();
        messageInput.setOnAction(event ->mainMenuController.sendMessageField(messageInput.getText()));
        messageInput.setPromptText("Введите сообщение");

        Button voiceMsgBtn= new Button("\uD83D\uDD0A"); // Микрофон или голосовое сообщение
        voiceMsgBtn.setPrefWidth(50);
        Button emojiBtn= new Button("\uD83D\uDE03");     // Эмодзи
        emojiBtn.setPrefWidth(50);
        Button imageBtn= new Button("\uD83D\uDCF7");     // Изображение
        imageBtn.setPrefWidth(50);

        HBox messageInputArea= new HBox(
                messageInput,
                voiceMsgBtn,
                emojiBtn,
                imageBtn
        );
        messageInput.prefWidthProperty().bind(messageInputArea.widthProperty());
        messageInputArea.setSpacing(5);

// Общий блок с сообщениями и вводом
        return new VBox(headerTopRow, messagesScroll, messageInputArea );
    }

    private Region spacer() {
        Region spacer=new Region();
        HBox.setHgrow(spacer , Priority.ALWAYS );
        return spacer;
    }
}