package fr.ensim.interop.bigbrain.infrastructure.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import fr.ensim.interop.bigbrain.domain.weather.City;
import fr.ensim.interop.bigbrain.domain.weather.Meteo;
import fr.ensim.interop.bigbrain.domain.weather.WeatherRepository;
import fr.ensim.interop.bigbrain.infrastructure.weather.model.OpenWeather;



@Repository
public class OpenWeatherRepository implements WeatherRepository {

    @Value("${open.weather.api.url}")
    private String URL;

    @Value("${open.weather.api.token}")
    private String TOKEN;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public City findCityByName(String cityName) {
        ResponseEntity<City[]> responseEntity = restTemplate.getForEntity(
                URL + "geo/1.0/direct?q={cityName}&limit=3&appid=" + TOKEN,
                City[].class, cityName);
        City[] cities = responseEntity.getBody();
        return (cities != null && cities.length > 0) ? cities[0] : null;
    }

    @Override
    public Meteo[] findWeatherByCity(City city, int days) {
        OpenWeather openWeather = restTemplate.getForObject(
                URL + "data/2.5/forecast?lat={lat}&lon={lon}&units=metric&lang=fr&appid="
                        + TOKEN,
                OpenWeather.class, city.getLat(), city.getLon());
        if (openWeather == null) {
            return new Meteo[0];
        }
        Meteo[] meteo = new Meteo[days];
        for (int i = 0; i < days; i++) {
            meteo[i] = new Meteo();
            meteo[i].setDescription(openWeather.getDataList().get(i).getWeather().get(0).getDescription());
            meteo[i].setTemperature(Double.parseDouble(openWeather.getDataList().get(i).getMain().getTemp()));
        }
        return meteo;
    }
}
