package fr.ensim.interop.bigbrain.presentation.telegramBot.executor;

import fr.ensim.interop.bigbrain.application.weather.GetWeatherUseCase;
import fr.ensim.interop.bigbrain.domain.weather.Meteo;

public class ForecastsExecutor implements Executor {

    private final GetWeatherUseCase getWeatherUseCase;

    public ForecastsExecutor(GetWeatherUseCase getWeatherUseCase) {
        this.getWeatherUseCase = getWeatherUseCase;
    }

    @Override
    public String execute(String[] message, Long userId) {
        if (message.length < 2) {
            return "Please provide a city name";
        }

        String cityName = extractCityName(message);
        boolean moreFlag = containsMoreFlag(message);
        int days = moreFlag ? 3 : 1;

        return getWeatherForecast(cityName, days);
    }

    private String extractCityName(String[] message) {
        StringBuilder cityNameBuilder = new StringBuilder();
        for (int i = 1; i < message.length; i++) {
            if ("more".equalsIgnoreCase(message[i])) {
                break;
            }
            if (i > 1) {
                cityNameBuilder.append(" ");
            }
            cityNameBuilder.append(message[i]);
        }
        return cityNameBuilder.toString();
    }

    private boolean containsMoreFlag(String[] message) {
        for (String part : message) {
            if ("more".equalsIgnoreCase(part)) {
                return true;
            }
        }
        return false;
    }

    private String getWeatherForecast(String cityName, int days) {
        try {
            Meteo[] meteoArray = getWeatherUseCase.execute(cityName, days);
            StringBuilder responseBuilder = new StringBuilder();
            for (int i = 0; i < meteoArray.length; i++) {
                responseBuilder.append("Prévision pour le jour ").append(i + 1).append(" : ")
                        .append(meteoArray[i].toString()).append("\n");
            }
            return responseBuilder.toString();
        } catch (Exception e) {
            return "Erreur lors de la récupération de la météo : " + e.getMessage();
        }
    }
}