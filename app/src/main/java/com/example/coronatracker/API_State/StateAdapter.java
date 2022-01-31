package com.example.coronatracker.API_State;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.API_State.Models.Regional;
import com.example.coronatracker.R;

import java.text.NumberFormat;
import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> {

    Context context;
    List<Regional> list;

    public StateAdapter(Context context, List<Regional> list) {
        this.context = context;
        this.list = list;
    }

    public void filterdlist(List<Regional> filterlist){
        list = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_state_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StateAdapter.ViewHolder holder, int position) {

       Regional regional = list.get(position);

        holder.sno.setText(Integer.toString(position+1));

        holder.statename.setText(regional.getLoc());
        holder.statecases.setText(NumberFormat.getInstance().format(regional.getTotalConfirmed()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,StateDetailActivity.class);
                intent.putExtra("stateName",regional.getLoc());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sno,statename,statecases;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sno=(TextView) itemView.findViewById(R.id.call_sn_no);
            statename = (TextView) itemView.findViewById(R.id.statename_card);
            statecases = (TextView) itemView.findViewById(R.id.txt_cases_state);
        }
    }
}
