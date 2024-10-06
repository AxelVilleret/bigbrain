package fr.ensim.interop.bigbrain.domain.chat;

import java.util.ArrayList;

public class UserConversation extends ArrayList<MessageWithAnswer> {
    private Long userId;

    public UserConversation(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void addMessage(String content, String response) {
        this.add(new MessageWithAnswer(new OldMessage(content, true), new OldMessage(response, false)));
    }

    @Override
    public String toString() {
        return "UserConversation{" +
                "userId=" + userId +
                ", messages=" + super.toString() +
                '}';
    }
    
}
