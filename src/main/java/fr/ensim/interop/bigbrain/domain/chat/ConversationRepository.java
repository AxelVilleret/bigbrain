package fr.ensim.interop.bigbrain.domain.chat;

public interface ConversationRepository {

    public UserConversation findConversationByUserId(Long userId);

    public void addConversation(UserConversation conversation);

    public void updateConversation(Long userId, String content, String response);

    public boolean isConversationEmpty(Long userId);
    
}
