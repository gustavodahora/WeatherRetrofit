package dev.gustavodahora.weatherretrofit.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.net.HttpURLConnection;
import java.util.List;

import dev.gustavodahora.weatherretrofit.SearchCity;
import dev.gustavodahora.weatherretrofit.model.geocoderapi.Geocoding;
import dev.gustavodahora.weatherretrofit.retroint.APIGeocoderCall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUtilGeocoder {

    String BASE_URL = "https://api.openweathermap.org/geo/1.0/";
    Context context;
    Activity activity;
    String city;
    SearchCity searchCity;

    public APIUtilGeocoder(Context context,
                           Activity activity,
                           String city,
                           SearchCity searchCity) {
        this.context = context;
        this.activity = activity;
        this.city = city;
        this.searchCity = searchCity;
    }

    public void callApi() {
        try {
            // Retrofit Builder
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            searchCity.showDialog();

            // instance for interface
            APIGeocoderCall apiGeocoderCall = retrofit.create(APIGeocoderCall.class);

            Call<List<Geocoding>> call = apiGeocoderCall.getGeocoder(city);

            call.enqueue(new Callback<List<Geocoding>>() {
                @Override
                public void onResponse(@NonNull Call<List<Geocoding>> call, @NonNull Response<List<Geocoding>> response) {
                    if (response.code() != HttpURLConnection.HTTP_OK && response.body() != null) {
                        return;
                    }
                        assert response.body() != null;
                        searchCity.generateRecycle(response.body());
                        searchCity.dismissDialog();
                }

                @Override
                public void onFailure(@NonNull Call<List<Geocoding>> call, @NonNull Throwable t) {
                    Toast.makeText(activity,
                            "Error = " + t,
                            Toast.LENGTH_LONG).show();
                    searchCity.dismissDialog();
                }
            });
        } catch (Exception e) {
            Toast.makeText(activity,
                    "Error = " + e,
                    Toast.LENGTH_LONG).show();
            searchCity.dismissDialog();
        }
    }
}
