package org.example.chatclientjava41;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import org.example.chatclientjava41.dto.MessageDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.example.chatclientjava41.MainMenuController.sendMessageField;

public class MainMenuView{
    private final  MainMenuController mainMenuController;
    private BorderPane leftShard;
    private BorderPane centerShard;
    private BorderPane root;
    private Contact currentContact=null;


    private long userId;

    public MainMenuView(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    public Scene MainScene(){
        root = new BorderPane();//потребовалось разделить основной borderPane на побочные т.к. для обновления VBox с сообщениями обновляло и ввод сообщения(т.е. каждый пару секунд стирало все что ты не успел отправить), ну и тоже самое с панелью контактов
        centerShard=new BorderPane();
        leftShard=new BorderPane();
        UserSearchComponent userSearch = new UserSearchComponent(); // Теперь загрузка внутри компонента
        userSearch.setOnUserClicked(user -> mainMenuController.CreateContact(user.id()));//консумер-интерфейс для применения разных функций для одного типа объектов,
        // т.е. тут можно задать другие функции но промежуточный просчет будет темже
        leftShard.setTop(userSearch.getView());
        createContactsPane();
        CenterShardChat();
        TopAndBottomShardChat();
        root.setLeft(leftShard);
        root.setRight(createProfilePane());
        root.setCenter(centerShard);
        Scene scene = new Scene(root, 1200, 500);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/StyleMainMenu.css")).toExternalForm());
        return scene;
    }

    public void createContactsPane() {  //оптимальнее всего в каждой функции сборки отдельного vbox устанавливать его в borderPane, а не возвращать для сборки в другом месте.
                                        // Таким образом мы можем в любой момент заставить отрисовать конкретную часть окна, а не пересобирать все окно заново
        VBox list=new VBox();
        List<Contact> contacts=ApplicationState.getApplicationState().getContacts();
        if(contacts!=null){
            for (int i = 0; i<contacts.size(); i++) {
                HBox contactItem = new HBox();
                Contact interlocutor=contacts.get(i);
                Circle avatarCircle = new Circle(20, Color.LIGHTGRAY);
                Label initialsLabel = new Label(interlocutor.getUserDTO().firstName().charAt(0)+"");
                StackPane avatarStack = new StackPane(avatarCircle, initialsLabel);// Аватарка
                Label nameLabel = new Label(interlocutor.getUserDTO().firstName()+" " + interlocutor.getUserDTO().lastName());
                Label lastMsgLabel = new Label("Последнее сообщение...");
                contactItem.getChildren().addAll(avatarStack, new VBox(nameLabel, lastMsgLabel));
                contactItem.setOnMousePressed(e -> {
                    contactItem.setStyle("-fx-background-color: #cccccc;"); // Эффект нажатия
                    currentContact=interlocutor;
                    TopAndBottomShardChat();
                    CenterShardChat();
                });
                list.getChildren().add(contactItem);
                VBox.setMargin(contactItem, new Insets(5));
            }
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(list);
        VBox contactsPane = new VBox(scrollPane);
        contactsPane.setPadding(new Insets(10));
        contactsPane.setSpacing(10);
        contactsPane.setPrefWidth(300);
        contactsPane.setStyle("-fx-background-color: #f0f0f0;");
        leftShard.setCenter(contactsPane);
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
        Stream.of(viewProfileBtn, findInChatBtn, sendImageBtn, moreInfoBtn, logout)
                .forEach(btn -> btn.setMaxWidth(Double.MAX_VALUE));//метод применяющий макс размер к кнопкам
        logout.setOnAction(actionEvent -> mainMenuController.Logout());
        VBox buttonsBox = new VBox(viewProfileBtn,findInChatBtn,sendImageBtn,moreInfoBtn,logout);// Кнопки профиля
        profileVBox.getChildren().addAll(avatarStack,nameLabel,lastSeenLabel,buttonsBox);
        return profileVBox;
    }
    public void TopAndBottomShardChat() {
        if(currentContact!=null){
            //____________шапка чата________________
            Label nameLbl = new Label(currentContact.getUserDTO().username());
            Label timeLbl = new Label("Последняя активность: 12:45");
            timeLbl.setFont(Font.font(12));
            Button callBtn = new Button("Звонок");
            Button videoCallBtn = new Button("Видео звонок");
            HBox header = new HBox(new VBox(nameLbl, timeLbl), callBtn, videoCallBtn);
            header.setAlignment(Pos.CENTER_LEFT);
            centerShard.setTop(header);
            //________________низ чата______________________________
            TextField messageInput= new TextField();
            messageInput.setOnAction(event -> sendMessageField(messageInput.getText(),currentContact.getUserDTO().id()));
            messageInput.setPromptText("Введите сообщение");
            Button voiceMsgBtn= new Button("\uD83D\uDD0A"); // Микрофон или голосовое сообщение
            voiceMsgBtn.setPrefWidth(50);
            Button emojiBtn= new Button("\uD83D\uDE03");     // Эмодзи
            voiceMsgBtn.setPrefWidth(50);
            Button imageBtn= new Button("\uD83D\uDCF7");     // Изображение
            voiceMsgBtn.setPrefWidth(50);
            HBox messageInputArea= new HBox(messageInput,voiceMsgBtn,emojiBtn,imageBtn);
            messageInput.prefWidthProperty().bind(messageInputArea.widthProperty());
            messageInputArea.setSpacing(5);
            centerShard.setBottom(messageInputArea);
        }else{
            centerShard.setTop(new VBox());
            centerShard.setBottom(new VBox());
        }
    }

    public void CenterShardChat(){//____________поле чата___________________
        if(currentContact!=null){
            ArrayList<MessageDTO> messages = currentContact.getMessages();
            VBox messagesVbox=new VBox();
            if(messages!=null){
                for (MessageDTO messageDTO : messages) {
                    HBox bubbleHBox= new HBox();
                    Label messageLbl= new Label(messageDTO.content());
                    messageLbl.borderProperty().set(new Border(new BorderStroke(Color.GRAY,BorderStrokeStyle.SOLID,new CornerRadii(5),new BorderWidths(1)))
                    );
                    if(messageDTO.senderId()==userId){
                        bubbleHBox.setAlignment(Pos.CENTER_RIGHT);
                        messageLbl.backgroundProperty().set(new Background(new BackgroundFill(Color.LIGHTGREEN,new CornerRadii(5),Insets.EMPTY)));
                        bubbleHBox.getChildren().addAll(messageLbl);
                    } else{
                        bubbleHBox.setAlignment(Pos.CENTER_LEFT);
                        messageLbl.backgroundProperty().set(new Background(new BackgroundFill(Color.WHITE,new CornerRadii(5),Insets.EMPTY)));
                        bubbleHBox.getChildren().addAll(messageLbl);
                    }
                    messagesVbox.getChildren().add(bubbleHBox);
                }
            }
            ScrollPane scrollPane=new ScrollPane();
            scrollPane.setContent(messagesVbox);
            scrollPane.setFitToWidth(true);
            scrollPane.setVvalue(1.0);
            centerShard.setCenter(scrollPane);
        }else centerShard.setCenter(new ScrollPane(new VBox()));
    }

    public void setCurrentContact() {
        currentContact = null;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}