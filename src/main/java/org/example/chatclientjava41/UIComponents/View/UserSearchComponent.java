package org.example.chatclientjava41.UIComponents.View;

import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.chatclientjava41.Data.AllResponse;
import org.example.chatclientjava41.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class UserSearchComponent {//консумер-интерфейс для применения разных функций для одного типа объектов, т.е. это
    private final TextField searchField = new TextField();
    private final ListView<UserDTO> userList = new ListView<>();
    private List<UserDTO> allUsers;
    private Consumer<UserDTO> userClickHandler;
    private boolean isLoaded = false;
    private final VBox root = new VBox(5); // Основной контейнер

    public UserSearchComponent() {
        setupUI();
    }

    private void setupUI() {
        searchField.setPromptText("Поиск...");
        // Изначально скрываем список
        userList.setVisible(false);
        userList.setManaged(false);
        userList.setPrefHeight(100);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!isLoaded && !newVal.isEmpty()) {
                loadUsers();
                isLoaded = true;
                userList.setManaged(true);
            }
            if (newVal.isEmpty()) {
                userList.setVisible(false); // Скрываем список при пустом поле
                userList.setManaged(false);
            } else {
                if (isLoaded) {
                    filterUsers(newVal);
                    userList.setVisible(true); // Показываем список при вводе
                    userList.setManaged(true);
                }
            }
        });
        userList.setCellFactory(lv -> new ListCell<>() {
            {
                // Стиль при наведении
                setOnMouseEntered(e -> {
                    if (!isEmpty()) {
                        setStyle("-fx-background-color: #df5555;");
                    }
                });

                setOnMouseExited(e -> {
                    if (!isEmpty()) {
                        setStyle("");
                    }
                });

                // Обработка кликов
                setOnMouseClicked(e -> {
                    if (e.getClickCount() == 2 && !isEmpty() && userClickHandler != null) {
                        UserDTO selectedUser = getItem();
                        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmAlert.setTitle("Подтверждение");
                        confirmAlert.setHeaderText("Добавить контакт?");
                        confirmAlert.setContentText(
                                "Вы точно хотите добавить " +
                                        selectedUser.firstName() + " в контакты?"
                        );

                        confirmAlert.setOnHidden(evt -> {
                            userList.setVisible(false);  // Скрываем ListView
                            searchField.clear();        // Очищаем поле поиска
                        });
                        Optional<ButtonType> result = confirmAlert.showAndWait();
                        if (result.isPresent() && result.get() == ButtonType.OK) {
                            userClickHandler.accept(selectedUser); // Действие при подтверждении
                        }
                    }
                    e.consume(); // Предотвращаем всплытие события
                });
            }

            @Override
            protected void updateItem(UserDTO user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty ? null : user.id() + " | " + user.firstName() + " " + user.lastName() + " | " + user.username());
            }
        });

        root.getChildren().addAll(searchField, userList);
    }

    public void setOnUserClicked(Consumer<UserDTO> handler) {
        this.userClickHandler = handler;
    }

    private void loadUsers() {
        allUsers = AllResponse.getUsers();
        userList.getItems().setAll(allUsers);
        userList.setManaged(true);
    }

    private void filterUsers(String query) {
        List<UserDTO> filtered = allUsers.stream()
                .filter(user -> containsIgnoreCase(user, query))
                .toList();
        userList.getItems().setAll(filtered);
    }

    private boolean containsIgnoreCase(UserDTO user, String query) {
        if (query == null || query.isEmpty()) return false;
        String lowerQuery = query.toLowerCase();
        return user.firstName().toLowerCase().contains(lowerQuery) ||
                user.lastName().toLowerCase().contains(lowerQuery) ||
                user.username().toLowerCase().contains(lowerQuery);
    }

    public Parent getView() {
        return root;
    }
}