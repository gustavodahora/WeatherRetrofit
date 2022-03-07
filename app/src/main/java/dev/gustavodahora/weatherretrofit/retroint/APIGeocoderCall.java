package dev.gustavodahora.weatherretrofit.retroint;

import java.util.List;

import dev.gustavodahora.weatherretrofit.model.geocoderapi.Geocoding;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIGeocoderCall {
    // https://run.mocky.io/v3/
    // a8385ac6-c51f-4cdd-9a41-7e3f2eb77a9c

    @GET("direct?q=London&limit=5&appid=97c5571d9fc1e803990872a56dbe037f")
    Call<List<Geocoding>> getGeocoder();
}
