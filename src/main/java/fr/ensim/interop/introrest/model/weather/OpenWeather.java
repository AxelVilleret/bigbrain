package fr.ensim.interop.introrest.model.weather;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeather {

    @JsonProperty("cod")
    private String code;

    @JsonProperty("list")
    private List<WeatherData> dataList;

    @JsonProperty("message")
    private long message;

    public long getMessage() {
        return message;
    }

    public void setMessage(long message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<WeatherData> getDataList() {
        return dataList;
    }

    public void setDataList(List<WeatherData> dataList) {
        this.dataList = dataList;
    }


    @Override
    public String toString() {
        return "WeatherForecast{" +
                "code='" + code + '\'' +
                ", dataList=" + dataList +
                ", message='" + message + '\'' +
                '}';
    }
}
