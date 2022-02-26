package com.example.coronatracker.CALLnSMS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.coronatracker.R;

import java.util.ArrayList;

public class CallActiviy extends AppCompatActivity {

    private ArrayList<Model> model ;
    TextView centralCall;

    private RecyclerView recyclerView;
    private CallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_activiy);

        recyclerView = (RecyclerView) findViewById(R.id.contactRV);
        centralCall = (TextView) findViewById(R.id.centralCall);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        centralCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = "+91-11-23978046";
                String call = "tel:"+mobile.trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(call));
                startActivity(intent);
            }
        });

        model=new ArrayList<>();

        model.add(new Model("Andhra Pradesh", "0866-2410978"));
        model.add(new Model("Andaman and Nicobar", "03192-232102"));
        model.add(new Model("Arunachal Pradesh","9436055743"));
        model.add(new Model("Assam","6913347770"));
        model.add(new Model("Bihar","104"));
        model.add(new Model("Chhattisgarh","104"));
        model.add(new Model("Chandigarh","9779558282"));
        model.add(new Model("Dadra and Nagar Haveli and Daman & Diu","104"));
        model.add(new Model("Delhi","011-22307145"));
        model.add(new Model("Goa","104"));
        model.add(new Model("Gujarat","104"));
        model.add(new Model("Haryana","8558893911"));
        model.add(new Model("Himachal Pradesh","104"));
        model.add(new Model("Jammu & Kashmir ","01912520982"));
        model.add(new Model("Karnataka ","104"));
        model.add(new Model("Kerala","0471-2552056"));
        model.add(new Model("Ladakh","01982256462"));
        model.add(new Model("Lakshadweep ","104"));
        model.add(new Model("Madhya Pradesh ","104"));
        model.add(new Model("Maharashtra","020-26127394"));
        model.add(new Model("Manipur","3852411668"));
        model.add(new Model("Meghalaya","108"));
        model.add(new Model("Mizoram","102"));
        model.add(new Model("Nagaland","7005539653"));
        model.add(new Model("Odisha","9439994859"));
        model.add(new Model("Punjab","104"));
        model.add(new Model("Puducherry","104"));
        model.add(new Model("Rajasthan","0141-2225624"));
        model.add(new Model("Sikkim","104"));
        model.add(new Model("Tamil Nadu","044-29510500"));
        model.add(new Model("Telangana","104"));
        model.add(new Model("Tripura","0381-2315879"));
        model.add(new Model("Uttarakhand","104"));
        model.add(new Model("Uttar Pradesh","18001805145"));
        model.add(new Model("West Bengal ","1800313444222"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new CallAdapter(model,CallActiviy.this);
        recyclerView.setAdapter(adapter);

    }


}