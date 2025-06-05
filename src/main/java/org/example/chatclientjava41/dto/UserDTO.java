package org.example.chatclientjava41.dto;

public record UserDTO(
        long id,
        String username,
        String firstName,
        String lastName,
        String photoUrl
) {
    // record автоматически создает:
    // - все поля final private
    // - конструктор
    // - геттеры (с именем как у поля, без get-префикса)
    // - equals(), hashCode(), toString()
    // - DTO — только переносит данные между слоями (только поля, без логики).
}