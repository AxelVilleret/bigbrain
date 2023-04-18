package fr.ensim.interop.introrest.bot.executor;

import org.springframework.web.client.RestTemplate;

import fr.ensim.interop.introrest.bot.BotImpl;
import fr.ensim.interop.introrest.model.weather.Meteo;

public class ForecastsExecutor extends Executor {

    @Override
    public String execute(String[] commands) {
        RestTemplate restTemplate = new RestTemplate();
        String response;
        String url;
        String ville = "";
        if (commands.length >= 2 && !commands[commands.length-1].equals("more")) {
            for (int i = 1; i < commands.length; i++) {
                ville += commands[i] + " ";
            }
            url = BotImpl.URL + "weather?ville=" + ville;
        } else if (commands.length >= 3 && commands[commands.length-1].equals("more")) {
            for (int i = 1; i < commands.length-1; i++) {
                ville += commands[i] + " ";
            }
            url = BotImpl.URL + "weather?ville=" + ville + "&more=more";
        } else {
            response = "Veuillez renseigner votre ville...";
            return response;
        }
        response = "";
        try {
            Meteo[] responseApi = restTemplate.getForObject(url, Meteo[].class);
            for (int i = 0; i < responseApi.length; i++) {
                response = response + BotImpl.INTROSMETEO[i] + responseApi[i] + "\n";
            }
        } catch (Exception e) {
            response = "Désolé mais je ne connais pas cette ville...";
        }
        return response;
    }
    
}
