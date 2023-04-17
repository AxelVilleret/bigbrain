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
        if (commands.length == 2 || (commands.length >= 3 && !commands[2].equals("more"))) {
            url = BotImpl.URL + "weather?ville=" + commands[1];
        } else if (commands.length >= 3 && commands[2].equals("more")) {
            url = BotImpl.URL + "weather?ville=" + commands[1] + "&more=more";
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
