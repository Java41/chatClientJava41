package org.example.chatclientjava41.dto;

public record MessageDTO (
        long id,
        long senderId,
        String senderUsername,
        long recipientId,
        String recipientUsername,
        String content,
        String timestamp
){
    @Override
    public long senderId() {
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
