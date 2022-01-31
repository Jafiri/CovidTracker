package com.example.coronatracker.CALLnSMS;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.API_State.StateAdapter;
import com.example.coronatracker.R;

import java.util.List;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.Myholder> {

     List<Model> stateNumbers;
     Context context;

    public CallAdapter(List<Model> stateNumbers, Context context) {
        this.stateNumbers = stateNumbers;
        this.context = context;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customcallingcard,parent,false);
        return new Myholder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {

        Model model = stateNumbers.get(position);

        holder.sno.setText(Integer.toString(position+1));

        holder.statename.setText(model.name);
        holder.statenumber.setText(model.mobile);


                holder.callbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mobileno = model.getMobile();
                        String call = "tel:"+mobileno.trim();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(call));
                        context.startActivity(intent);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return stateNumbers.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {

        TextView sno, statename, statenumber;
        ImageView callbtn;
        LinearLayout callbutton;

        public Myholder(@NonNull View itemView) {
            super(itemView);

            sno = (TextView) itemView.findViewById(R.id.call_sn_no);
            statename = (TextView) itemView.findViewById(R.id.call_state_name);
            statenumber = (TextView) itemView.findViewById(R.id.call_state_number);
            callbtn = (ImageView) itemView.findViewById(R.id.call_btn);
            callbutton = (LinearLayout) itemView.findViewById(R.id.callbutton);

        }
    }
}
