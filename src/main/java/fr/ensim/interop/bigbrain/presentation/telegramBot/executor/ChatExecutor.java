package fr.ensim.interop.bigbrain.presentation.telegramBot.executor;

import fr.ensim.interop.bigbrain.application.chat.GetResponseUseCase;
import fr.ensim.interop.bigbrain.domain.chat.UserMessage;

public class ChatExecutor implements Executor {

    GetResponseUseCase getResponseUseCase;

    public ChatExecutor(GetResponseUseCase getResponseUseCase) {
        this.getResponseUseCase = getResponseUseCase;
    }

    @Override
    public String execute(String[] message, Long userId) {
        if (message.length < 2) {
            return "Please provide a message";
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 1; i < message.length; i++) {
            if (i > 1) {
                messageBuilder.append(" ");
            }
            messageBuilder.append(message[i]);
        }

        return getResponseUseCase.execute(new UserMessage(messageBuilder.toString(), userId));
    }

    
    
}
