package dev.gustavodahora.weatherretrofit.model.appweather;

public class AppWeatherData {
    String cityName;
    double kelvinTemp;
    String celsius;

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
