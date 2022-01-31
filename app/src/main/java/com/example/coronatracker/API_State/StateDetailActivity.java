package com.example.coronatracker.API_State;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.coronatracker.API_Country.CountryDetailActivity;
import com.example.coronatracker.API_State.Models.Regional;
import com.example.coronatracker.API_State.Models.StateModel;
import com.example.coronatracker.HomeActivity;
import com.example.coronatracker.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StateDetailActivity extends AppCompatActivity {

    AnyChartView stateChart;
    TextView sname,sTotalConfirm,sConfirmedCasesIndian,sConfirmedCasesForeign,sDischarged,sDeaths;
    List<Regional> regionals;
    String name;
    int totalConfirm,confirmedCasesIndian,confirmedCasesForeign,discharged,deaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_detail);

        init();


        name = getIntent().getStringExtra("stateName");
        sname.setText(name);

        ApiCallBack();

    }


    private void ApiCallBack() {

        String url = "https://api.rootnet.in/covid19-in/stats/latest";
        String BASE_URL="https://api.rootnet.in/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        Call<StateModel> call;

        call = requestInterface.getStateData(url);

        call.enqueue(new Callback<StateModel>() {
            @Override
            public void onResponse(Call<StateModel> call, Response<StateModel> response) {
                StateModel stateModel = response.body();
                regionals = stateModel.getData().getRegional();

                for(int i =0;i<regionals.size();i++){
                    if(regionals.get(i).getLoc().equals(name)){
                        totalConfirm = regionals.get(i).getTotalConfirmed();
                        confirmedCasesIndian = regionals.get(i).getConfirmedCasesIndian();
                        confirmedCasesForeign = regionals.get(i).getConfirmedCasesForeign();
                        discharged = regionals.get(i).getDischarged();
                        deaths = regionals.get(i).getDeaths();

                        sTotalConfirm.setText(NumberFormat.getInstance().format(totalConfirm));
                        sConfirmedCasesIndian.setText(NumberFormat.getInstance().format(confirmedCasesIndian));
                        sConfirmedCasesForeign.setText(NumberFormat.getInstance().format(confirmedCasesForeign));
                        sDischarged.setText(NumberFormat.getInstance().format(discharged));
                        sDeaths.setText(NumberFormat.getInstance().format(deaths));

                        Pie pie = AnyChart.pie();

                        List<DataEntry> dataEntries = new ArrayList<>();
                        dataEntries.add(new ValueDataEntry("Total Confirm",totalConfirm));
                        dataEntries.add(new ValueDataEntry("Indians",confirmedCasesIndian));
                        dataEntries.add(new ValueDataEntry("Foreigners",confirmedCasesForeign));
                        dataEntries.add(new ValueDataEntry("Discharged",discharged));
                        dataEntries.add(new ValueDataEntry("Deaths",deaths));

                        pie.data(dataEntries);
                        pie.title(name+" Covid Cases Stats");
                        pie.labels().position("outside");



                        pie.legend()
                                .position("center-bottom")
                                .itemsLayout(LegendLayout.HORIZONTAL)
                                .align(Align.CENTER).fontSize(16);

                        stateChart.setChart(pie);

                    }
                }

            }

            @Override
            public void onFailure(Call<StateModel> call, Throwable t) {
                Toast.makeText(StateDetailActivity.this, "Error.." + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    public void init(){
        stateChart = (AnyChartView) findViewById(R.id.stateChartView);

        sname = (TextView) findViewById(R.id.stateName);
        sTotalConfirm = (TextView) findViewById(R.id.stateTotalConfirm);
        sConfirmedCasesIndian = (TextView) findViewById(R.id.stateConfirmedCasesIndian);
        sConfirmedCasesForeign = (TextView) findViewById(R.id.stateConfirmedCasesForeign);
        sDischarged = (TextView) findViewById(R.id.stateDischarged);
        sDeaths = (TextView) findViewById(R.id.stateDeaths);
    }

}