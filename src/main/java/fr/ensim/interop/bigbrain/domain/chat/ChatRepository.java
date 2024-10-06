package fr.ensim.interop.bigbrain.domain.chat;

public interface ChatRepository {

    public String getResponse(String message, UserConversation conversation);

}
