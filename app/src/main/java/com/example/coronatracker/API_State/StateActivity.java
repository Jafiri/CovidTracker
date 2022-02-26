package com.example.coronatracker.API_State;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coronatracker.API_State.Models.Regional;
import com.example.coronatracker.API_State.Models.StateModel;
import com.example.coronatracker.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StateActivity extends AppCompatActivity {

   private RecyclerView recyclerView;
    private StateAdapter adapter;
    private List<Regional> list;
    EditText searchbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state);

        recyclerView = (RecyclerView) findViewById(R.id.rv_state);
        searchbar = (EditText) findViewById(R.id.search_bar_state);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new StateAdapter(this, list);
        recyclerView.setAdapter(adapter);

        ApiResponse();
    }

    private void ApiResponse() {

        list.clear();

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
                List<Regional> regionals = stateModel.getData().getRegional();

               for(int i=0;i<regionals.size();i++){
                   list.add(new Regional(regionals.get(i).getLoc(),regionals.get(i).getConfirmedCasesIndian()
                                         ,regionals.get(i).getConfirmedCasesForeign(),regionals.get(i).getDischarged()
                                          , regionals.get(i).getDeaths(),regionals.get(i).getTotalConfirmed()));
               }
               adapter.notifyDataSetChanged();

                Toast.makeText(StateActivity.this, "Got data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<StateModel> call, Throwable t) {
                Toast.makeText(StateActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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

    private void filter(String toString) {

        List<Regional> filterlist = new ArrayList<>();
        for(Regional items :list){
            if (items.getLoc().toLowerCase().toLowerCase().contains(toString.toLowerCase())){
                filterlist.add(items);
            }
    }
        adapter.filterdlist(filterlist);
}
}