package dev.gustavodahora.weatherretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import dev.gustavodahora.weatherretrofit.model.WeatherData;
import dev.gustavodahora.weatherretrofit.retroint.APIWeatherCall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tvCity;
    TextView tvTempActual;
    TextView tvTempMaximum;
    TextView tvTempMinimum;

    String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
        callApi();
    }

    public void setupViews() {
        tvCity = findViewById(R.id.tv_city);
        tvTempActual = findViewById(R.id.tv_temp_actual);
        tvTempMaximum = findViewById(R.id.tv_temp_max);
        tvTempMinimum = findViewById(R.id.tv_temp_min);
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
                public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                    // Checking for the response
                    if (response.code() != 200) {
                        Toast.makeText(MainActivity.this, getString(R.string.error_api_call), Toast.LENGTH_LONG).show();
                        return;
                    }

                    tvTempActual.setText(response.body().getMain().getTemp().toString());
                    tvTempMaximum.setText(response.body().getMain().getTempMax().toString());
                    tvTempMinimum.setText(response.body().getMain().getTempMin().toString());
                }

                @Override
                public void onFailure(Call<WeatherData> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error = " + t, Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Error = " + e, Toast.LENGTH_LONG).show();
        }
    }
}