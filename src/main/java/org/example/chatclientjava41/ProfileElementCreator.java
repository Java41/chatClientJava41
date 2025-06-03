package org.example.chatclientjava41;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static org.example.chatclientjava41.AllResponse.updateProfileFieldsAPI;


public class ProfileElementCreator {
    private static final double PROFILE_WINDOW_WIDTH = 450;
    private static final double PROFILE_WINDOW_HEIGHT = 350;
    private static final Insets ELEMENT_PADDING = new Insets(8, 10, 8, 10);
    private static final int ELEMENT_SPACING = 10;

    public static VBox createMyProfileInteractiveElement() {
        ApplicationState appState = ApplicationState.getApplicationState();
        String currentUsername = appState.getUsername() != null ?
                appState.getUsername() : "Пользователь";

        VBox profileElementContainer = createProfileContainer();
        HBox interactiveProfileHBox = createInteractiveProfileBox(currentUsername);

        interactiveProfileHBox.setOnMouseClicked(event -> showProfileWindow(appState));
        profileElementContainer.getChildren().add(interactiveProfileHBox);

        return profileElementContainer;
    }

    private static VBox createProfileContainer() {
        VBox container = new VBox();
        container.setMaxWidth(Double.MAX_VALUE);
        return container;
    }

    private static HBox createInteractiveProfileBox(String username) {
        HBox profileBox = new HBox();
        profileBox.setMaxWidth(Double.MAX_VALUE);
        profileBox.setSpacing(ELEMENT_SPACING);
        profileBox.setAlignment(Pos.CENTER_LEFT);
        profileBox.setPadding(ELEMENT_PADDING);

        applyProfileBoxStyles(profileBox);

        Label profileLabel = new Label(username);
        profileLabel.setFont(Font.font("System", 14));
        profileBox.getChildren().add(profileLabel);

        return profileBox;
    }

    private static void applyProfileBoxStyles(HBox profileBox) {
        String baseStyle = "-fx-background-radius: 6; -fx-border-radius: 6; -fx-border-width: 1;";
        String normalStyle = "-fx-background-color: #f0f0f0; -fx-border-color: #f17575; " + baseStyle;
        String hoverStyle = "-fx-background-color: #fc0808; -fx-border-color: #fc0808; " + baseStyle;

        profileBox.setStyle(normalStyle);
        profileBox.setOnMouseEntered(e -> profileBox.setStyle(hoverStyle));
        profileBox.setOnMouseExited(e -> profileBox.setStyle(normalStyle));
    }

    private static void showProfileWindow(ApplicationState appState) {
        System.out.println("Открываем окно профиля...");

        Stage profileStage = createProfileStage();
        VBox profileLayout = createProfileLayout(appState);

        Scene profileScene = new Scene(profileLayout, PROFILE_WINDOW_WIDTH, PROFILE_WINDOW_HEIGHT);
        profileStage.setScene(profileScene);
        profileStage.showAndWait();
    }

    private static Stage createProfileStage() {
        Stage stage = new Stage();
        stage.setTitle("Мой профиль");
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

    private static VBox createProfileLayout(ApplicationState appState) {
        VBox layout = new VBox(ELEMENT_SPACING);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER_LEFT);

        addProfileFields(layout, appState);

        return layout;
    }


    private static void addProfileFields(VBox layout, ApplicationState appState) {
        layout.getChildren().addAll(
                new Label("Мой профиль"),
                new Separator(),
                createEditableField("ID", appState.getId(), false), // ID не редактируемый
                createEditableField("Имя пользователя", appState.getUsername(), true),
                createEditableField("Email", appState.getEmail(), true),
                createEditableField("Имя", appState.getFirstname(), true),
                createEditableField("Фамилия", appState.getLastname(), true),
                createEditableField("Дата рождения", appState.getBirthdate(), true),
                createEditableField("Роль", appState.getRole(), false) // Роль не редактируемая
        );
    }

    private static HBox createEditableField(String fieldName, String fieldValue, boolean editable) {
        HBox fieldContainer = new HBox(5);
        fieldContainer.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label(fieldName + ": ");
        nameLabel.setFont(Font.font(14));

        Label valueLabel = new Label(fieldValue != null ? fieldValue : "N/A");
        valueLabel.setFont(Font.font(14));

        if (editable) {
            String baseStyle =
                    "-fx-cursor: hand; " +
                            "-fx-text-fill: #387798; " +
                            "-fx-padding: 2 5px; " +
                            "-fx-border-radius: 5px;";

            String hoverStyle =
                    "-fx-background-color: #ffffff; " +  // темнее при наведении
                            "-fx-text-fill: #fc0808;";

            valueLabel.setStyle(baseStyle);

            valueLabel.setOnMouseEntered(e -> valueLabel.setStyle(baseStyle + hoverStyle));
            valueLabel.setOnMouseExited(e -> valueLabel.setStyle(baseStyle));

            makeLabelEditable(valueLabel, fieldName);
        }

        fieldContainer.getChildren().addAll(nameLabel, valueLabel);
        return fieldContainer;
    }

    private static void makeLabelEditable(Label label, String fieldName) {
        label.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                HBox parent = (HBox) label.getParent();
                int index = parent.getChildren().indexOf(label);

                TextField textField = new TextField(label.getText());
                parent.getChildren().set(index, textField);
                textField.selectAll();
                textField.requestFocus();

                // Сохраняем оригинальное значение для возможного отката
                String originalValue = label.getText();

                textField.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ENTER) {
                        saveChanges(label, textField.getText(), fieldName);
                        parent.getChildren().set(index, label);
                    } else if (e.getCode() == KeyCode.ESCAPE) {
                        label.setText(originalValue); // Восстанавливаем оригинальное значение
                        parent.getChildren().set(index, label);
                    }
                });

                // При потере фокуса без сохранения - отменяем изменения
                textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
                    if (!newVal) {
                        parent.getChildren().set(index, label);
                    }
                });
            }
        });
    }

    private static void saveChanges(Label label, String newValue, String fieldName) {
        label.setText(newValue);
        updateProfileFields(fieldName, newValue);
    }

    private static void updateProfileFields(String fieldName, String newValue) {

// Формируем JSON вручную
        String jsonData;
        switch (fieldName) {
            case "Имя пользователя":
                jsonData = String.format("{\"username\":\"%s\"}", newValue);
                break;
            case "Email":
                jsonData = String.format("{\"email\":\"%s\"}", newValue);
                break;
            case "Имя":
                jsonData = String.format("{\"firstName\":\"%s\"}", newValue);
                break;
            case "Фамилия":
                jsonData = String.format("{\"lastName\":\"%s\"}", newValue);
                break;
            case "Дата рождения":
                jsonData = String.format("{\"birthDate\":\"%s\"}", newValue);
                break;
            default:
                return; // Неизвестное поле

        }
        System.out.println("Попытка обновить поле " + fieldName + " на: " + newValue);
        System.out.println("Вызываем метод updateProfileFieldsAPI и передаем туда JSON: "+jsonData);
        updateProfileFieldsAPI(jsonData);

    }
}