package fr.ensim.interop.bigbrain.domain.weather;

public interface WeatherRepository {
    City findCityByName(String cityName);
    Meteo[] findWeatherByCity(City city, int days);
}
