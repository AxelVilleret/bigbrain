package fr.ensim.interop.introrest;

import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import fr.ensim.interop.introrest.model.Joke;
import fr.ensim.interop.introrest.model.telegram.ApiResponseTelegram;
import fr.ensim.interop.introrest.model.telegram.ApiResponseUpdateTelegram;
import fr.ensim.interop.introrest.model.telegram.Update;
import fr.ensim.interop.introrest.model.telegram.perso.UserMessage;
import fr.ensim.interop.introrest.model.weather.Meteo;

public class ResponseBot extends TimerTask {

    private final static String URL = "http://localhost:9090/";
    private final static String[] INTROSMETEO = {"Pour la météo d'aujourd'hui:\n", "Pour la météo de demain:\n", "Pour la météo d'après-demain:\n"};

    private Update last;

    private boolean first = true;

    private Update next;

    private String response = "";

    public void setNext() {
        RestTemplate restTemplate = new RestTemplate();
        last = next;
        if (next == null) {
            ApiResponseUpdateTelegram response = restTemplate.getForObject(URL + "update", ApiResponseUpdateTelegram.class);
            if (!response.getResult().isEmpty())
                next = response.getResult().get(0);
        } else {
            long offset = next.getUpdateId();
            ApiResponseUpdateTelegram response = restTemplate
                    .getForObject(URL + "update?offset=" + offset, ApiResponseUpdateTelegram.class);
            if (response.getResult().size() > 1)
                next = response.getResult().get(1);
        }
    }

    public void sendMessageToclient() {
        UserMessage message = new UserMessage();
        message.setText(response);
        message.setChat_id(next.getMessage().getChat().getId());
        new RestTemplate().postForObject("http://localhost:9090/message", message, ApiResponseTelegram.class);
    }

    public ResponseBot setTextToHelloWorld() {
        response = "Hello World !";
        return this;
    }

    private ResponseBot setTextToForecasts(String[] splitedMessage) {
        RestTemplate restTemplate = new RestTemplate();
        String url;
        if (splitedMessage.length == 2 || (splitedMessage.length >= 3 && !splitedMessage[2].equals("more"))) {
            url = "http://localhost:9090/weather?ville=" + splitedMessage[1];
        } else if (splitedMessage.length >= 3 && splitedMessage[2].equals("more")) {
            url = "http://localhost:9090/weather?ville=" + splitedMessage[1] + "&more=more";
        } else {
            response = "Veuillez renseigner votre ville...";
            return this;
        }
        response = "";
        try {
            Meteo[] responseApi = restTemplate.getForObject(url, Meteo[].class);
            for (int i = 0; i < responseApi.length; i++) {
                response = response + INTROSMETEO[i] + responseApi[i] + "\n";
            }
        } catch (Exception e) {
            response = "Désolé mais je ne connais pas cette ville...";
        }
        return this;
    }

    private ResponseBot setTextToblague() {
        RestTemplate restTemplate = new RestTemplate();
        Joke joke = restTemplate.getForObject(URL + "joke", Joke.class);
        response = joke.toString();
        return this;
    }

    public ResponseBot setTextToDefault() {
        response = "Désolé mais ce n'est pas une action valide...";
        return this;
    }

    private ResponseBot setTextToNotice() {
        response = "Voici ce dont je suis capable :\n" + 
        "- /hello_world -> Afficher la célèbre réplique.\n" + 
        "- /meteo <ville> -> Afficher les prévisions pour la ville donnée pour 1 jour.\n" +
        "- /meteo <ville> <more> -> Afficher les prévisions pour la ville donnée pour 3 jours.\n" +
        "- /blague -> Afficher une blague aléatoirement.\n";
        return this;
    }

    public void respondToClient() {
        setNext();
        if (first == true || (next !=null && !last.getUpdateId().equals(next.getUpdateId()))) {
            String messageClient = next.getMessage().getText();
            String[] splitedMessage = messageClient.split(" ");
            switch (splitedMessage[0]) {
                case "/hello_world":
                    setTextToHelloWorld().sendMessageToclient();
                    break;
                case "/meteo":
                    setTextToForecasts(splitedMessage).sendMessageToclient();
                    break;
                case "/blague":
                    setTextToblague().sendMessageToclient();
                    break;
                case "/aide":
                    setTextToNotice().sendMessageToclient();
                    break;
                default:
                    setTextToDefault().sendMessageToclient();
                    break;
            }
            first = false;
        }
    }

    @Override
    public void run() {
        respondToClient();
    }
    
}
