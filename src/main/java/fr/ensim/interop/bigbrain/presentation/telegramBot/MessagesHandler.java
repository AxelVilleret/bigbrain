package fr.ensim.interop.bigbrain.presentation.telegramBot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import fr.ensim.interop.bigbrain.presentation.telegramBot.executor.Executor;
import fr.ensim.interop.bigbrain.presentation.telegramBot.executor.ExecutorFactory;

@Component
public class MessagesHandler extends TelegramLongPollingBot {

    @Value("${telegram.bot.id}")
    private String telegramApiToken;

    @Value("${telegram.api.botusername}")
    private String telegramApiBotUsername;

    private final ExecutorFactory executorFactory;

    public MessagesHandler(ExecutorFactory executorFactory) {
        this.executorFactory = executorFactory;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String messageClient = update.getMessage().getText();
        Long idClient = update.getMessage().getFrom().getId();
        Integer idMessage = update.getMessage().getMessageId();
        String[] splitedMessage = messageClient.split(" ");
        SendMessage message;
        try {
            Executor e = executorFactory.getExecutor(splitedMessage[0]);
            message = new SendMessage(String.valueOf(idClient), e.execute(splitedMessage, idClient));
        } catch (UnsupportedOperationException ex) {
            message = new SendMessage(String.valueOf(idClient), "Commande inconnue. Utilisez une commande valide.");
        }
        message.setReplyToMessageId(idMessage);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return telegramApiToken.substring(3);
    }

    @Override
    public String getBotUsername() {
        return telegramApiBotUsername;
    }
}