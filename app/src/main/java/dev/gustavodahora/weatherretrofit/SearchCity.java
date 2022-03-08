package dev.gustavodahora.weatherretrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import dev.gustavodahora.weatherretrofit.model.geocoderapi.Geocoding;
import dev.gustavodahora.weatherretrofit.util.APIUtilGeocoder;
import dev.gustavodahora.weatherretrofit.util.DBShared;
import dev.gustavodahora.weatherretrofit.util.SnackBarUtil;

public class SearchCity extends AppCompatActivity {

    Button btnSearch;
    EditText editTextSearch;
    LinearLayout lnErrorList;

    final DBShared dbShared = new DBShared();
    SharedPreferences pref;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        context = getApplicationContext();

        setupPref();
        setupViews();
    }

    public void setupViews() {
        editTextSearch = findViewById(R.id.edit_text_search);
        btnSearch = findViewById(R.id.btn_search);
        lnErrorList = findViewById(R.id.ln_error_show_list);

        btnSearch.setOnClickListener(v -> saveCity());
    }

    public void saveCity() {
//        ConstraintLayout constraintLayout = findViewById(R.id.main_view);
//        if (!editTextSearch.getText().toString().isEmpty()
//        ) {
//            dbShared.setCity(pref, editTextSearch.getText().toString());
//            startActivity(new Intent(SearchCity.this, MainActivity.class));
//            finish();
//        } else {
//            SnackBarUtil.showLong(constraintLayout, "Enter a city name", context);
//        }
        String city = editTextSearch.getText().toString();
        if (!city.isEmpty()) {
            APIUtilGeocoder apiUtilGeocoder = new APIUtilGeocoder(context,
                    this,
                    city,
                    this);
            apiUtilGeocoder.callApi();
        } else {
            ConstraintLayout view = findViewById(R.id.main_view);
            SnackBarUtil.showLong(view, getString(R.string.empty_field), context);
        }
    }

    public void setupPref() {
        pref = this.getSharedPreferences(
                "dev.gustavodahora.weatherretrofit",
                Context.MODE_PRIVATE);
    }

    public void generateRecycle(List<Geocoding> geocodingList) {
        if (geocodingList.isEmpty()) {
            showEmptyList();
        } else {
            dismissEmptyList();
        }
    }

    public void showEmptyList() {
        lnErrorList.setVisibility(View.VISIBLE);
    }

    public void dismissEmptyList() {
        lnErrorList.setVisibility(View.GONE);
    }

    public void showDialog() {

    }

}