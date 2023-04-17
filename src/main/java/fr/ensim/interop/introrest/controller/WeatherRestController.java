package fr.ensim.interop.introrest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.ensim.interop.introrest.model.weather.City;
import fr.ensim.interop.introrest.model.weather.Meteo;
import fr.ensim.interop.introrest.model.weather.OpenWeather;

@RestController
public class WeatherRestController {

        @Value("${open.weather.api.url}")
        private String openWeatherApiUrl;

        @Value("${open.weather.api.token}")
        private String openWeatherApiToken;

        // private Map<String, String> translations = new HashMap<>();

        // public WeatherRestController() {
        //         translations.put("Thunderstorm", "Orageux");
        //         translations.put("Drizzle", "Pluvieux (pluie fine)");
        //         translations.put("Rain", "Pluvieux");
        //         translations.put("Snow", "Neigeux");
        //         translations.put("Clear", "Eclaircis");
        //         translations.put("Clouds", "Nuageux");
        // }

        public OpenWeather getDatasOpenWeather(String nomVille) {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<City[]> responseEntity = restTemplate.getForEntity(
                                openWeatherApiUrl + "geo/1.0/direct?q={ville}&limit=3"
                                                + "&appid=" + openWeatherApiToken,
                                City[].class, nomVille);
                City[] cities = responseEntity.getBody();
                if (cities.length == 0)
                        return null;
                City city = cities[0];

                return restTemplate.getForObject(
                                openWeatherApiUrl + "data/2.5/forecast?lat={lat}"
                                                + "&lon={longitude}&units=metric&lang=fr&appid=" + openWeatherApiToken,
                                OpenWeather.class, city.getLat(), city.getLon());
        }

        @GetMapping("/weather")
        public ResponseEntity<Meteo[]> meteoByVille(
                        @RequestParam("ville") String nomVille,
                        @RequestParam(name = "more", required = false) String more) {

                OpenWeather openWeather = getDatasOpenWeather(nomVille);
                if (openWeather == null)
                        return ResponseEntity.badRequest().build();
                int nbDays = more == null ? 1 : 3;
                Meteo[] meteo = new Meteo[nbDays];
                for (int i = 0; i < nbDays; i++) {
                        meteo[i] = new Meteo();
                        meteo[i].setMeteo(
                                        openWeather.getDataList().get(i).getWeather().get(0).getDescription());
                        meteo[i].setTemperature(openWeather.getDataList().get(i).getMain().getTemp());
                }

                return ResponseEntity.ok().body(meteo);
        }
}
