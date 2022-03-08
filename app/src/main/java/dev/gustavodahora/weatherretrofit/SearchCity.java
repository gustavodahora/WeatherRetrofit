package dev.gustavodahora.weatherretrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;

import dev.gustavodahora.weatherretrofit.model.geocoderapi.Geocoding;
import dev.gustavodahora.weatherretrofit.util.APIUtilGeocoder;
import dev.gustavodahora.weatherretrofit.util.DBShared;
import dev.gustavodahora.weatherretrofit.util.SnackBarUtil;

public class SearchCity extends AppCompatActivity {

    Button btnSearch;
    EditText editTextSearch;
    LinearLayout lnErrorList;
    RecyclerView recyclerView;
    LinearProgressIndicator linearProgressBar;

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
        recyclerView = findViewById(R.id.recycler_view);
        linearProgressBar = findViewById(R.id.linear_progress_bar);

        btnSearch.setOnClickListener(v -> saveCity());
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveCity();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
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
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new CustomAdapter(geocodingList));
        }
    }

    public void showEmptyList() {
        lnErrorList.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public void dismissEmptyList() {
        lnErrorList.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    public void dismissDialog() {
        linearProgressBar.show();
        linearProgressBar.setVisibility(View.GONE);
    }

    public void showDialog() {
        linearProgressBar.setVisibility(View.VISIBLE);
    }

    public static class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private List<Geocoding> cities;
        public CustomAdapter (List<Geocoding> cities){
            this.cities = cities;
        }

        @NonNull
        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_search, parent, false);
            return new ViewHolder(rowItem);
        }

        @Override
        public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
            holder.cityName.setText(cities.get(position).getName());
            if (cities.get(position).getCountry() != null) {
                holder.tvCountry.setText(cities.get(position).getCountry());
            }
            if (cities.get(position).getState() != null) {
                holder.tvState.setText(cities.get(position).getState());
            }
        }

        @Override
        public int getItemCount() {
            return this.cities.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView cityName;
            private final TextView tvCountry;
            private final TextView tvState;

            public ViewHolder(View view) {
                super(view);
                cityName = view.findViewById(R.id.city_name);
                tvCountry = view.findViewById(R.id.tv_country);
                tvState = view.findViewById(R.id.tv_state);
            }
        }
    }

}