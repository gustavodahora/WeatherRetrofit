package dev.gustavodahora.weatherretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashControl extends AppCompatActivity {

    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_control);
        // Method Region

        setupGetCity();
    }

    public void setupGetCity() {
        SharedPreferences pref = getSharedPreferences(
                "dev.gustavodahora.weatherretrofit",
                MODE_PRIVATE);

        city = pref.getString("city", "NO_CITY");

        if (city.equals("NO_CITY")) startSearchCity();
        else startMain();
    }

    public void startMain() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashControl.this, MainActivity.class));
            finish();
        }, 1000);
    }

    public void startSearchCity() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashControl.this, SearchCity.class));
            finish();
        }, 1000);
    }
}