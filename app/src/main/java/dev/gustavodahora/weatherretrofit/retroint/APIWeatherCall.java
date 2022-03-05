package dev.gustavodahora.weatherretrofit.retroint;

import dev.gustavodahora.weatherretrofit.model.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIWeatherCall {
    // https://run.mocky.io/v3/
    // a8385ac6-c51f-4cdd-9a41-7e3f2eb77a9c

    String API_KEY = "97c5571d9fc1e803990872a56dbe037f";

    @GET("weather?lat=-23.5249082&lon=-46.3445663&&appid=97c5571d9fc1e803990872a56dbe037f")
    Call<WeatherData> getWeatherData();
}
