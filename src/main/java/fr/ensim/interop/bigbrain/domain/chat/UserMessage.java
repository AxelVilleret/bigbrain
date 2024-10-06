package fr.ensim.interop.bigbrain.domain.chat;

public class UserMessage {
    private String content;
    private Long userId;

    public UserMessage(String content, Long userId) {
        this.content = content;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public Long getUserId() {
        return userId;
    }
}
