package com.example.coronatracker.API_Country;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coronatracker.API_State.StateActivity;
import com.example.coronatracker.HomeActivity;
import com.example.coronatracker.News.NewsActivity;
import com.example.coronatracker.PaymentActivity;
import com.example.coronatracker.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryDetailActivity extends AppCompatActivity {


    List<CountryModel> countrylist;

    private TextView totalconfirm, totalactive, totalrecovered, totaldeath, totaltest;
    private TextView todayconfirm, todayrecovered, todaydeath, date, countyname, statename;
    BottomAppBar bottomNavigationView;
    FloatingActionButton floatingActionButton;
    String country = "India";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        init();



        bottomNavigationView.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(CountryDetailActivity.this, HomeActivity.class));
                        finish();
                        break;
                    case R.id.payment:
                        startActivity(new Intent(CountryDetailActivity.this, PaymentActivity.class));
                        finish();
                        break;
                    case R.id.News:
                        startActivity(new Intent(CountryDetailActivity.this, NewsActivity.class));
                        finish();
                        break;
                }
                return false;
            }
        });

        countrylist = new ArrayList<>();

        if (getIntent().getStringExtra("country") != null) {
            country = getIntent().getStringExtra("country");
        }
        countyname.setText(country);


        statename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CountryDetailActivity.this, StateActivity.class));
            }
        });


        countyname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CountryDetailActivity.this, CountryActivity.class));

            }
        });
        ApiCallBack();

    }

    private void ApiCallBack() {

        ApiUtilities.getapiInterface().getCountryData().enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                countrylist.addAll(response.body());

                for (int i = 0; i < countrylist.size(); i++) {
                    if (countrylist.get(i).getCountry().equals(country)) {

                        int confirm = countrylist.get(i).getCases();
                        int active = Integer.parseInt(countrylist.get(i).getActive());
                        int recovered = Integer.parseInt(countrylist.get(i).getRecovered());
                        int death = Integer.parseInt(countrylist.get(i).getDeaths());
                        int test = Integer.parseInt(countrylist.get(i).getTests());
                        int deathtoday = Integer.parseInt(countrylist.get(i).getTodayDeaths());
                        int confirmtoday = Integer.parseInt(countrylist.get(i).getTodayCases());
                        int recoverytoday = Integer.parseInt(countrylist.get(i).getTodayRecovered());


                        totalconfirm.setText(NumberFormat.getInstance().format(confirm));
                        totalactive.setText(NumberFormat.getInstance().format(active));
                        totalrecovered.setText(NumberFormat.getInstance().format(recovered));
                        totaldeath.setText(NumberFormat.getInstance().format(death));
                        totaltest.setText(NumberFormat.getInstance().format(test));

                        setText(countrylist.get(i).getUpdated());

                        // todaydeath.setText(NumberFormat.getInstance().format(deathtoday));
                        //  todayconfirm.setText(NumberFormat.getInstance().format(confirmtoday));
                        //   todayrecovered.setText(NumberFormat.getInstance().format(recoverytoday));


//                        AnimatedPieView mAnimatedPieView = findViewById(R.id.pi_chart);
//                        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
//                        config.startAngle(-90)// Starting angle offset
//                                .addData(new SimplePieInfo(confirm, Color.parseColor("#FBC233")))//Data (bean that implements the IPieInfo interface)
//                                .addData(new SimplePieInfo(active, Color.parseColor("#007afe")))
//                                .addData(new SimplePieInfo(recovered, Color.parseColor("#7EC544")))
//                                .addData(new SimplePieInfo(death, Color.parseColor("#F6404F"))).splitAngle(1f)
//                                .duration(1500);// draw pie animation duration
//
//                        mAnimatedPieView.applyConfig(config);
//                        mAnimatedPieView.start();


                    }
                }
            }

            @Override
            public void onFailure(Call<List<CountryModel>> call, Throwable t) {
                Toast.makeText(CountryDetailActivity.this, "Error.." + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setText(String updated) {
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd,yyyy");

        long millisecond = Long.parseLong(updated);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisecond);

        date.setText("Updated at\n       " + dateFormat.format(calendar.getTime()));
    }

    public void init() {
        totalconfirm = (TextView) findViewById(R.id.totalconfirm);
        totalactive = (TextView) findViewById(R.id.totalactive);
        totalrecovered = (TextView) findViewById(R.id.totalrecovered);
        totaldeath = (TextView) findViewById(R.id.totaldeath);
        totaltest = (TextView) findViewById(R.id.totaltest);
        //todayconfirm = (TextView) findViewById(R.id.todayconfirm);
        //todayrecovered = (TextView) findViewById(R.id.todayrecovered);
        //todaydeath = (TextView) findViewById(R.id.todaydeath);
        date = (TextView) findViewById(R.id.date);
        countyname = (TextView) findViewById(R.id.cname);
        statename = (TextView) findViewById(R.id.sname);
        bottomNavigationView = (BottomAppBar) findViewById(R.id.bottom_nav);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }
}