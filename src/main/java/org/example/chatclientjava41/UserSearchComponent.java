package org.example.chatclientjava41;

import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.chatclientjava41.dto.UserDTO;
import java.util.List;
import java.util.function.Consumer;

public class UserSearchComponent {
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
        userList.setVisible(false); // Изначально скрываем список

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!isLoaded && !newVal.isEmpty()) {
                loadUsers();
                isLoaded = true;
            }

            if (newVal.isEmpty()) {
                userList.setVisible(false); // Скрываем список при пустом поле
                userList.getItems().clear();
            } else {
                if (isLoaded) {
                    filterUsers(newVal);
                    userList.setVisible(true); // Показываем список при вводе
                }
            }
        });

        userList.setCellFactory(lv -> new ListCell<>() {
            {
                setOnMouseClicked(e -> {
                    if (!isEmpty() && userClickHandler != null) {
                        userClickHandler.accept(getItem());
                    }
                });
            }

            @Override
            protected void updateItem(UserDTO user, boolean empty) {
                super.updateItem(user, empty);
                setText(empty ? null : user.firstName() + " " + user.lastName());
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