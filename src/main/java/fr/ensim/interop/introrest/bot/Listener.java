package fr.ensim.interop.introrest.bot;

import java.util.TimerTask;

import org.springframework.web.client.RestTemplate;

import fr.ensim.interop.introrest.model.telegram.ApiResponseUpdateTelegram;
import fr.ensim.interop.introrest.model.telegram.Update;

public class Listener extends TimerTask {

    private Listener() {}
    
    private Bot bot;

    private Update last;

    private boolean first = true;

    private Update next;

    public Listener(Bot bot) {
        this.bot = bot;
    }

    public void setNext() {
        RestTemplate restTemplate = new RestTemplate();
        last = next;
        if (next == null) {
            ApiResponseUpdateTelegram response = restTemplate.getForObject(BotImpl.URL + "update",
                    ApiResponseUpdateTelegram.class);
            if (!response.getResult().isEmpty())
                next = response.getResult().get(0);
        } else {
            long offset = next.getUpdateId();
            ApiResponseUpdateTelegram response = restTemplate
                    .getForObject(BotImpl.URL + "update?offset=" + offset, ApiResponseUpdateTelegram.class);
            if (response.getResult().size() > 1)
                next = response.getResult().get(1);
        }
    }

    public boolean hasNext() {
        setNext();
        return first || (next != null && !last.getUpdateId().equals(next.getUpdateId()));
    }

    @Override
    public void run() {
        if (hasNext()) {
            bot.onUpdatereceived(next);
            first = false;
        }
    }


}
