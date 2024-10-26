package fr.ensim.interop.bigbrain.infrastructure.chat.model;

public class Message {
    public String role;
    public String content;

    public Message() {
    }
    
    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "Message [content=" + content + ", role=" + role + "]";
    }
}