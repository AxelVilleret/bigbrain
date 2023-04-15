package fr.ensim.interop.introrest.model.weather;

public class Meteo {
    private String meteo;
    private String temperature;

    public Meteo() {}

    public String getMeteo() {
        return meteo;
    }

    public void setMeteo(String meteo) {
        this.meteo = meteo;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "D'après mes renseignements, il va faire " + temperature + " °C et la météo sera plutôt " + meteo + " !\n";
    }
}
