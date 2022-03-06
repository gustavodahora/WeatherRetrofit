package dev.gustavodahora.weatherretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dev.gustavodahora.weatherretrofit.util.DBShared;

public class SearchCity extends AppCompatActivity {

    Button btnSearch;
    EditText editTextSearch;

    final DBShared dbShared = new DBShared();
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);

        pref = this.getSharedPreferences(
                "dev.gustavodahora.weatherretrofit",
                Context.MODE_PRIVATE);

        setupViews();
    }

    public void setupViews() {
        editTextSearch = findViewById(R.id.edit_text_search);
        btnSearch = findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(v -> saveCity());
    }

    public void saveCity() {
        if (!editTextSearch.getText().toString().isEmpty()
        ) {
            dbShared.setCity(pref, editTextSearch.getText().toString());
            startActivity(new Intent(SearchCity.this, MainActivity.class));
        }
    }
}