package dev.gustavodahora.weatherretrofit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import dev.gustavodahora.weatherretrofit.model.appweather.AppWeatherData;
import dev.gustavodahora.weatherretrofit.util.APIUtil;
import dev.gustavodahora.weatherretrofit.util.DBShared;
import dev.gustavodahora.weatherretrofit.util.SnackBarUtil;

public class MainActivity extends AppCompatActivity {

    TextView tvCity;
    TextView tvTempActual;
    ImageButton btnRefresh;
    TextView tvFeelsLike;
    TextView tvHumidity;
    ImageView imgAlert;

    AppWeatherData appWeatherData;
    ProgressDialog pd;

    String city;

    Context context;

    final DBShared dbShared = new DBShared();

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        pref = this.getSharedPreferences(
                "dev.gustavodahora.weatherretrofit",
                Context.MODE_PRIVATE);

        getCityName();
        setupViews();
        callApi();
    }

    public void setupViews() {
        tvCity = findViewById(R.id.tv_city);
        tvTempActual = findViewById(R.id.tv_temp_actual);
        btnRefresh = findViewById(R.id.btn_refresh);
        tvFeelsLike = findViewById(R.id.tv_feels_like);
        tvHumidity = findViewById(R.id.tv_humidity);
        imgAlert = findViewById(R.id.img_alert);

        btnRefresh.setOnClickListener(v -> callApi());
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
            tvFeelsLike.setText(appWeatherData.getFeelsLike());
            tvHumidity.setText(appWeatherData.getHumidity());
            setupHumidity();
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

    public void setupHumidity() {
        int actualHumidity = appWeatherData.getHumidityInt();
        if (actualHumidity < 12) {
            imgAlert.setImageResource(R.drawable.ic_alert_final);
        } else if (actualHumidity <= 20) {
            imgAlert.setImageResource(R.drawable.ic_alert_middle);
        } else if (actualHumidity <= 30) {
            imgAlert.setImageResource(R.drawable.ic_alert_start);
        } else {
            imgAlert.setImageResource(R.drawable.ic_alert_good);
        }
    }

    public String getCityName() {
        city = dbShared.getCity(pref);
        return city;
    }
}