package com.example.coronatracker.News.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.News.Models.CategoryRVModel;
import com.example.coronatracker.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.MyHolder> {

    private ArrayList<CategoryRVModel> categoryRVModelArrayList;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoryRVAdapter(ArrayList<CategoryRVModel> categoryRVModelArrayList, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRVModelArrayList = categoryRVModelArrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryRVAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category_card, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.MyHolder holder, @SuppressLint("RecyclerView") int position) {

        CategoryRVModel categoryRVModel = categoryRVModelArrayList.get(position);

        holder.TVCategory.setText(categoryRVModel.getCategory());
        Picasso.get().load(categoryRVModel.getCategoryImageUrl()).into(holder.IVCategory);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRVModelArrayList.size();
    }

    public interface CategoryClickInterface {
        void onCategoryClick(int position);
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView IVCategory;
        TextView TVCategory;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            IVCategory = (ImageView) itemView.findViewById(R.id.idIVCategory);
            TVCategory = (TextView) itemView.findViewById(R.id.TVCategory);

        }
    }
}
