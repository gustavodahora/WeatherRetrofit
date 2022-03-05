package dev.gustavodahora.weatherretrofit.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Locale;

import dev.gustavodahora.weatherretrofit.MainActivity;
import dev.gustavodahora.weatherretrofit.R;
import dev.gustavodahora.weatherretrofit.model.appweather.AppWeatherData;
import dev.gustavodahora.weatherretrofit.model.weatherapi.WeatherData;
import dev.gustavodahora.weatherretrofit.retroint.APIWeatherCall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUtil {

    String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    AppWeatherData appWeatherData;
    Context context;
    Activity activity;
    Response<WeatherData> responseData;
    MainActivity mainActivity;

    public APIUtil(AppWeatherData appWeatherData,
                   Context context,
                   Activity activity,
                   MainActivity mainActivity) {
        this.appWeatherData = appWeatherData;
        this.context = context;
        this.activity = activity;
        this.mainActivity = mainActivity;
    }

    public void callApi() {
        try {
            // Retrofit Builder
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // instance for interface
            APIWeatherCall apiWeatherCall = retrofit.create(APIWeatherCall.class);

            Call<WeatherData> call = apiWeatherCall.getWeatherData();

            call.enqueue(new Callback<WeatherData>() {
                @Override
                public void onResponse(@NonNull Call<WeatherData> call,
                                       @NonNull Response<WeatherData> response) {
                    // Checking for the response
                    if (response.code() != 200) {
                        mainActivity.dismissDialog();
                        Toast.makeText(activity,
                                activity.getString(R.string.error_api_call),
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (response.body() != null) {
                        responseData = response;
                        setupAppWeatherData();
                        mainActivity.dismissDialog();
                    } else {
                        mainActivity.dismissDialog();
                        Toast.makeText(context,
                                activity.getString(R.string.error_api_call),
                                Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<WeatherData> call,
                                      @NonNull Throwable t) {
                    mainActivity.dismissDialog();
                    Toast.makeText(activity,
                            "Error = " + t,
                            Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            mainActivity.dismissDialog();
            Toast.makeText(activity,
                    "Error = " + e,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void setupAppWeatherData() {
        if (responseData != null) {
            if (responseData.body() != null) {
                appWeatherData.setCityName(responseData.body().getName());
            }
            appWeatherData.setKelvinTemp(responseData.body().getMain().getTemp());
            appWeatherData.setCelsius(kelvinToCelsius(responseData.
                    body().getMain().getTemp()));
            mainActivity.responseSetupText();
        }
    }

    public String kelvinToCelsius(Double kelvin) {
        Double celsius =  kelvin - 273.15F;
        Locale current = activity.getResources().getConfiguration().locale;
        return String.format(current, "%.0f°C", celsius);
    }
}
