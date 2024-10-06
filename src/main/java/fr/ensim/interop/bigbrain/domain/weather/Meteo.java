package fr.ensim.interop.bigbrain.domain.weather;

public class Meteo {
    private String description;
    private double temperature;

    public Meteo() {
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    
    @Override
    public String toString() {
        return "D'après mes renseignements, il va faire " + temperature + " °C et la météo sera plutôt " + description
                + " !\n";
    }
}
