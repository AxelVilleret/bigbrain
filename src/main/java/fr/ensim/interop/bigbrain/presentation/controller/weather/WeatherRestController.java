package fr.ensim.interop.bigbrain.presentation.controller.weather;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ensim.interop.bigbrain.application.weather.GetWeatherUseCase;
import fr.ensim.interop.bigbrain.domain.weather.Meteo;

@RestController
public class WeatherRestController {

    private final GetWeatherUseCase getWeatherUseCase;

    public WeatherRestController(GetWeatherUseCase getWeatherUseCase) {
        this.getWeatherUseCase = getWeatherUseCase;
    }

    @GetMapping("/weather")
    public ResponseEntity<Meteo[]> getWeather(
            @RequestParam("city") String cityName,
            @RequestParam(name = "more", required = false) String more) {

        int days = (more == null) ? 1 : 3;
        Meteo[] meteo = getWeatherUseCase.execute(cityName, days);
        return ResponseEntity.ok().body(meteo);
    }
}
