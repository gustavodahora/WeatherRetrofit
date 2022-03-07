package dev.gustavodahora.weatherretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import dev.gustavodahora.weatherretrofit.util.DBShared;
import dev.gustavodahora.weatherretrofit.util.SnackBarUtil;

public class SearchCity extends AppCompatActivity {

    Button btnSearch;
    EditText editTextSearch;

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

        btnSearch.setOnClickListener(v -> saveCity());
    }

    public void saveCity() {
        ConstraintLayout constraintLayout = findViewById(R.id.main_view);
        if (!editTextSearch.getText().toString().isEmpty()
        ) {
            dbShared.setCity(pref, editTextSearch.getText().toString());
            startActivity(new Intent(SearchCity.this, MainActivity.class));
            finish();
        } else {
            SnackBarUtil.showLong(constraintLayout, "Enter a city name", context);
        }
    }

    public void setupPref() {
        pref = this.getSharedPreferences(
                "dev.gustavodahora.weatherretrofit",
                Context.MODE_PRIVATE);
    }
}