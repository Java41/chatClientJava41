package org.example.chatclientjava41.dto;

public record MessageDTO (
        String id,
        String senderId,
        String senderUsername,
        String recipientId,
        String recipientUsername,
        String content,
        String timestamp
){
    @Override
    public String senderId() {
        return senderId;
    }

    @Override
    public String content() {
        return content;
    }

    @Override
    public String timestamp() {
        return timestamp;
    }
}
