package com.example.coronatracker.News.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coronatracker.News.Models.Articles;
import com.example.coronatracker.News.NewsDetailActivity;
import com.example.coronatracker.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.MyHolder> {

    ArrayList<Articles> articlesArrayList;
    Context context;

    public NewsRVAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsRVAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_news_card,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.MyHolder holder, int position) {

        Articles articles = articlesArrayList.get(position);

        holder.titleTV.setText(articles.getTitle());
        holder.subTitleTV.setText(articles.getDescription());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("title",articles.getTitle());
                intent.putExtra("content",articles.getContent());
                intent.putExtra("desc",articles.getDescription());
                intent.putExtra("image",articles.getUrlToImage());
                intent.putExtra("url",articles.getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private ImageView newsIV;
        private TextView titleTV,subTitleTV;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = (TextView) itemView.findViewById(R.id.TVNews);
            subTitleTV = (TextView) itemView.findViewById(R.id.idTVSubTitle);
            newsIV = (ImageView) itemView.findViewById(R.id.idIVNews);
        }
    }
}
