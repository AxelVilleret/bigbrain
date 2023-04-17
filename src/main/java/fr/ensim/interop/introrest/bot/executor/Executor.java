package fr.ensim.interop.introrest.bot.executor;

import org.springframework.web.client.RestTemplate;

import fr.ensim.interop.introrest.bot.BotImpl;
import fr.ensim.interop.introrest.model.telegram.ApiResponseTelegram;
import fr.ensim.interop.introrest.model.telegram.perso.UserMessage;

public abstract class Executor {

    public void sendText(Long who, String what, Integer from) {
        UserMessage message = new UserMessage();
        message.setText(what);
        message.setChat_id(who);
        message.setReply_to_message_id(from);
        new RestTemplate().postForObject(BotImpl.URL + "message", message, ApiResponseTelegram.class);
    }

    public abstract String execute(String[] commands);


}
