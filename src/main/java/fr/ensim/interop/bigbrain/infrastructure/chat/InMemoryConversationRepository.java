package fr.ensim.interop.bigbrain.infrastructure.chat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import fr.ensim.interop.bigbrain.domain.chat.ConversationRepository;
import fr.ensim.interop.bigbrain.domain.chat.UserConversation;

@Component
public class InMemoryConversationRepository implements ConversationRepository {

    private Map<Long, UserConversation> conversations;

    public InMemoryConversationRepository() {
        this.conversations = new HashMap<>();
    }

    @Override
    public UserConversation findConversationByUserId(Long userId) {
        return conversations.get(userId);
    }

    @Override
    public void addConversation(UserConversation conversation) {
        conversations.put(conversation.getUserId(), conversation);
    }

    @Override
    public void updateConversation(Long userId, String content, String response) {
        UserConversation conversation = conversations.get(userId);
        conversation.addMessage(content, response);
    }
    
}
