package dev.gustavodahora.weatherretrofit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.squareup.picasso.Picasso;

import java.util.List;

import dev.gustavodahora.weatherretrofit.model.geocoderapi.Geocoding;
import dev.gustavodahora.weatherretrofit.util.APIUtilGeocoder;
import dev.gustavodahora.weatherretrofit.util.DBShared;

public class SearchCity extends AppCompatActivity {

    Button btnSearch;
    EditText editTextSearch;
    LinearLayout lnErrorList;
    RecyclerView recyclerView;
    LinearProgressIndicator linearProgressBar;

    final String endPointFlag = "https://countryflagsapi.com/png/";

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

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
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
            if (cities.get(position).getState() != null) {
                holder.tvState.setText(cities.get(position).getState());
            }
            holder.ctLayout.setOnClickListener(v -> callNextScreen(cities.get(position).getName()));
            try {
                Picasso.get().load(endPointFlag +
                        cities.get(position).getCountry())
                        .placeholder(R.drawable.ic_baseline_search_24)
                        .error(R.drawable.ic_baseline_close_24)
                        .into(holder.imgFlag);
            } catch (Exception e) {
                Toast.makeText(context, e + " ", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public int getItemCount() {
            return this.cities.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView cityName;
            private final TextView tvState;
            private final ConstraintLayout ctLayout;
            private final ImageView imgFlag;

            public ViewHolder(View view) {
                super(view);
                cityName = view.findViewById(R.id.city_name);
                tvState = view.findViewById(R.id.tv_state);
                ctLayout = view.findViewById(R.id.ct_layout);
                imgFlag = view.findViewById(R.id.img_flag);
            }
        }
    }

    public void callNextScreen(String cityName) {
        if (cityName.length() > 0) {
            dbShared.setCity(pref, cityName);
            startActivity(new Intent(SearchCity.this, MainActivity.class));
            finish();
        }
    }

}