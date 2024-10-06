package fr.ensim.interop.bigbrain.infrastructure.chat;

import org.springframework.stereotype.Repository;

import fr.ensim.interop.bigbrain.domain.chat.ChatRepository;
import fr.ensim.interop.bigbrain.domain.chat.UserConversation;

@Repository
public class OpenAIChatRepository implements ChatRepository {
    @Override
    public String getResponse(String content, UserConversation conversation) {
        return "Hello, how can I help you?";
    }
}