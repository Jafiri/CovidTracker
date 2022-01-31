package com.example.coronatracker.API_Country;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.coronatracker.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.MyHolder> {

    Context context;
    List<CountryModel> list;


    public CountryAdapter(Context context, List<CountryModel> list) {
        this.context = context;
        this.list = list;
    }

    public void filterdlist(List<CountryModel> filterlist){
        list = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_country_card,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        CountryModel data = list.get(position);

        holder.cases.setText(NumberFormat.getInstance().format(data.getCases()));
        holder.countryname.setText(data.getCountry());
        holder.serialno.setText(Integer.toString(position + 1));

        Map<String,String> flag = data.getCountryInfo();
        Glide.with(context).load(flag.get("flag")).into(holder.countryflag);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CountryDetailActivity.class);
                intent.putExtra("country",data.getCountry());
                context.startActivity(intent);

            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView serialno,countryname,cases;
        ImageView countryflag;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            serialno = (TextView) itemView.findViewById(R.id.serialno);
            countryname = (TextView) itemView.findViewById(R.id.countyname);
            cases = (TextView) itemView.findViewById(R.id.txt_cases_country);
            countryflag = (ImageView) itemView.findViewById(R.id.countryflag);

        }
    }
}
