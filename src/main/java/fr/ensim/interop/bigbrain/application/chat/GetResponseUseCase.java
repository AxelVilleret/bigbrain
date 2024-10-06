package fr.ensim.interop.bigbrain.application.chat;

import org.springframework.stereotype.Component;

import fr.ensim.interop.bigbrain.domain.chat.ChatRepository;
import fr.ensim.interop.bigbrain.domain.chat.ConversationRepository;
import fr.ensim.interop.bigbrain.domain.chat.UserConversation;
import fr.ensim.interop.bigbrain.domain.chat.UserMessage;

@Component
public class GetResponseUseCase {
    private final ChatRepository chatRepository;
    private final ConversationRepository conversationRepository;

    public GetResponseUseCase(ChatRepository chatRepository, ConversationRepository conversationRepository) {
        this.chatRepository = chatRepository;
        this.conversationRepository = conversationRepository;
    }

    public String execute(UserMessage userMessage) {
        UserConversation conversation = conversationRepository.findConversationByUserId(userMessage.getUserId());
        if (conversation == null) {
            conversationRepository.addConversation(new UserConversation(userMessage.getUserId()));
        } else {
            System.out.println("Conversation : " + conversation);
        }
        String response = chatRepository.getResponse(userMessage.getContent(), conversation);
        conversationRepository.updateConversation(userMessage.getUserId(), userMessage.getContent(), response);
        return response;

    }

}
