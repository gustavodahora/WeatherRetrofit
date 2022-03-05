package dev.gustavodahora.weatherretrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import dev.gustavodahora.weatherretrofit.model.appweather.AppWeatherData;
import dev.gustavodahora.weatherretrofit.util.APIUtil;
import dev.gustavodahora.weatherretrofit.util.SnackBarUtil;

public class MainActivity extends AppCompatActivity {

    TextView tvCity;
    TextView tvTempActual;

    AppWeatherData appWeatherData;
    ProgressDialog pd;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        setupViews();
        callApi();
    }

    public void setupViews() {
        tvCity = findViewById(R.id.tv_city);
        tvTempActual = findViewById(R.id.tv_temp_actual);
    }

    public void callApi() {
        showDialog();
        appWeatherData = new AppWeatherData();
        APIUtil apiUtil = new APIUtil(appWeatherData,
                context,
                this,
                this);
        apiUtil.callApi();
    }

    public void responseSetupText() {
        try {
            tvCity.setText(appWeatherData.getCityName());
            tvTempActual.setText(appWeatherData.getCelsius());
        } catch (Exception e) {
            Toast.makeText(context,
                    getString(R.string.error_api_call),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void showDialog() {
        pd = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        pd.setMessage(getString(R.string.loading_place));
        pd.show();
    }

    public void dismissDialog() {
        pd.dismiss();
    }

    public void showErrorToGetApi() {
        ConstraintLayout view = findViewById(R.id.main_view);
        SnackBarUtil.showLong(view, getString(R.string.no_internet), this);
    }
}