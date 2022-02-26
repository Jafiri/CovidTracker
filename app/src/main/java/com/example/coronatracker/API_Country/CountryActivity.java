package com.example.coronatracker.API_Country;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coronatracker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<CountryModel> list;
    ProgressDialog dialog;
    CountryAdapter adapter;
    EditText searchbar;
    TextView cases;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        recyclerView = (RecyclerView) findViewById(R.id.rv_country);
        searchbar = (EditText) findViewById(R.id.search_bar_country);
        cases = (TextView) findViewById(R.id.txt_cases_state);
        list = new ArrayList<>();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new CountryAdapter(this, list);
        recyclerView.setAdapter(adapter);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);
        dialog.show();


        ApiResponse();

        cases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(list, CountryModel.caseAsending);
                adapter.notifyDataSetChanged();
                }
        });
 }

    private void ApiResponse() {

        ApiUtilities.getapiInterface().getCountryData().enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                list.addAll(response.body());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<CountryModel>> call, Throwable t) {

                Toast.makeText(CountryActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String text) {

        List<CountryModel> filterList = new ArrayList<>();
        for (CountryModel items : list) {
            if (items.getCountry().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(items);
            }
        }
        adapter.filterdlist(filterList);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}