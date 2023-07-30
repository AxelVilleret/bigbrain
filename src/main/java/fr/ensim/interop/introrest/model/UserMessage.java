package fr.ensim.interop.introrest.model;

public class UserMessage {
    private Long userId;
    private String content;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
