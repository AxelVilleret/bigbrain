package fr.ensim.interop.introrest.bot;
import org.springframework.web.client.RestTemplate;

import fr.ensim.interop.introrest.model.Joke;
import fr.ensim.interop.introrest.model.telegram.ApiResponseTelegram;
import fr.ensim.interop.introrest.model.telegram.Update;
import fr.ensim.interop.introrest.model.telegram.perso.UserMessage;
import fr.ensim.interop.introrest.model.weather.Meteo;

public class BotImpl implements Bot {

    public final static String URL = "http://localhost:9090/";
    
    private final static String[] INTROSMETEO = {"Pour la météo d'aujourd'hui:\n", "Pour la météo de demain:\n", "Pour la météo d'après-demain:\n"};

    private String response = "";

    private Update update;

    @Override
    public void onUpdatereceived(Update update) {
        this.update = update;
        String messageClient = update.getMessage().getText();
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
    }

    public void sendMessageToclient() {
        UserMessage message = new UserMessage();
        message.setText(response);
        message.setChat_id(update.getMessage().getChat().getId());
        new RestTemplate().postForObject(URL + "message", message, ApiResponseTelegram.class);
    }

    public BotImpl setTextToHelloWorld() {
        response = "Hello World !";
        return this;
    }

    private BotImpl setTextToForecasts(String[] splitedMessage) {
        RestTemplate restTemplate = new RestTemplate();
        String url;
        if (splitedMessage.length == 2 || (splitedMessage.length >= 3 && !splitedMessage[2].equals("more"))) {
            url = URL + "weather?ville=" + splitedMessage[1];
        } else if (splitedMessage.length >= 3 && splitedMessage[2].equals("more")) {
            url = URL + "weather?ville=" + splitedMessage[1] + "&more=more";
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

    private BotImpl setTextToblague() {
        RestTemplate restTemplate = new RestTemplate();
        Joke joke = restTemplate.getForObject(URL + "joke", Joke.class);
        response = joke.toString();
        return this;
    }

    public BotImpl setTextToDefault() {
        response = "Désolé mais ce n'est pas une action valide...";
        return this;
    }

    private BotImpl setTextToNotice() {
        response = "Voici ce dont je suis capable :\n" + 
        "- /hello_world -> Afficher la célèbre réplique\n" + 
        "- /meteo <ville> -> Afficher les prévisions pour la ville donnée pour 1 jour\n" +
        "- /meteo ville <more> -> Afficher les prévisions pour la ville donnée pour 3 jours\n" +
        "- /blague -> Afficher une blague aléatoirement\n" + 
        "- /aide -> Afficher l'aide\n";
        return this;
    }
    
}
