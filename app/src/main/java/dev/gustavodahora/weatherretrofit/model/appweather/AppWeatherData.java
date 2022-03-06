package dev.gustavodahora.weatherretrofit.model.appweather;

public class AppWeatherData {
    String cityName;
    double kelvinTemp;
    String celsius;
    String feelsLike;
    String humidity;
    String speed;
    int humidityInt;

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public int getHumidityInt() {
        return humidityInt;
    }

    public void setHumidityInt(int humidityInt) {
        this.humidityInt = humidityInt;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getKelvinTemp() {
        return kelvinTemp;
    }

    public void setKelvinTemp(double kelvinTemp) {
        this.kelvinTemp = kelvinTemp;
    }

    public String getCelsius() {
        return celsius;
    }

    public void setCelsius(String celsius) {
        this.celsius = celsius;
    }
}
