package fr.ensim.interop.bigbrain.application.weather;

import org.springframework.stereotype.Component;

import fr.ensim.interop.bigbrain.domain.weather.City;
import fr.ensim.interop.bigbrain.domain.weather.Meteo;
import fr.ensim.interop.bigbrain.domain.weather.WeatherRepository;

@Component
public class GetWeatherUseCase {
    private final WeatherRepository weatherRepository;

    public GetWeatherUseCase(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Meteo[] execute(String cityName, int days) {
        City city = weatherRepository.findCityByName(cityName);
        if (city == null) {
            throw new IllegalArgumentException("City not found");
        }
        return weatherRepository.findWeatherByCity(city, days);
    }
}
