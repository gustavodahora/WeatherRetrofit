package dev.gustavodahora.weatherretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvCity;
    TextView tvTempActual;
    TextView tvTempMaximum;
    TextView tvTempMinimum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViews();
    }

    public void setupViews() {
        tvCity = findViewById(R.id.tv_city);
        tvTempActual = findViewById(R.id.tv_temp_actual);
        tvTempMaximum = findViewById(R.id.tv_temp_max);
        tvTempMinimum = findViewById(R.id.tv_temp_min);
    }
}