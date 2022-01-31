package com.example.coronatracker.News;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.coronatracker.HomeActivity;
import com.example.coronatracker.API_Country.CountryDetailActivity;
import com.example.coronatracker.News.Adapter.CategoryRVAdapter;
import com.example.coronatracker.News.Adapter.NewsRVAdapter;
import com.example.coronatracker.News.Models.Articles;
import com.example.coronatracker.News.Models.CategoryRVModel;
import com.example.coronatracker.News.Models.NewsModel;
import com.example.coronatracker.PaymentActivity;
import com.example.coronatracker.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    private RecyclerView newsRV,categoryRV;
    private ProgressBar lodingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModel> categoryRVModelArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;

    BottomAppBar bottomNavigationView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsRV = (RecyclerView) findViewById(R.id.idRVNews);
        categoryRV = (RecyclerView) findViewById(R.id.idRVCategory);
        lodingPB = (ProgressBar) findViewById(R.id.progressBar);
        bottomNavigationView = (BottomAppBar) findViewById(R.id.bottom_nav);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);


        bottomNavigationView.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(NewsActivity.this, HomeActivity.class));
                        finish();
                        break;
                    case R.id.payment:
                        startActivity(new Intent(NewsActivity.this, PaymentActivity.class));
                        finish();
                        break;
                }
                return false;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this, CountryDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        articlesArrayList = new ArrayList<>();
        categoryRVModelArrayList = new ArrayList<>();

        categoryRVAdapter = new CategoryRVAdapter(categoryRVModelArrayList,this,this::onCategoryClick);
        newsRVAdapter = new NewsRVAdapter(articlesArrayList,this);

        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryClick(int position) {

        String category = categoryRVModelArrayList.get(position).getCategory();
        getNews(category);
        newsRVAdapter.notifyDataSetChanged();
    }

    public void getCategories(){
        categoryRVModelArrayList.add(new CategoryRVModel("All","https://media.istockphoto.com/photos/television-streaming-multimedia-wall-concept-picture-id1287677376?b=1&k=20&m=1287677376&s=170667a&w=0&h=wvu0lKn4WbfHtKId83KzrHvGmBP7zn7ZwGEWmU99HWE="));
        categoryRVModelArrayList.add(new CategoryRVModel("Health","https://media.istockphoto.com/photos/the-world-after-covid19-article-picture-id1224333792?b=1&k=20&m=1224333792&s=170667a&w=0&h=BNJHGeLcqGBAh8xFVVrBv1je_WrtPgZ5wWE4Bo5BTBk="));
        categoryRVModelArrayList.add(new CategoryRVModel("Technology","https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fHRlY2hub2xvZ3klMjBuZXdzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Science","https://media.istockphoto.com/photos/female-chemist-at-work-in-laboratory-picture-id637785818?b=1&k=20&m=637785818&s=170667a&w=0&h=BFOAFUMdVxMWc-a3w-m_00djwLalBINEHGNHToflXMM="));
        categoryRVModelArrayList.add(new CategoryRVModel("Sports","https://media.istockphoto.com/photos/soccer-field-with-illumination-green-grass-and-cloudy-sky-background-picture-id1293105095?b=1&k=20&m=1293105095&s=170667a&w=0&h=1guu6B_WTHw5B4EShizGVRf3pyeBNNaGbtowrOLVjyM="));
        categoryRVModelArrayList.add(new CategoryRVModel("General","https://images.unsplash.com/photo-1432821596592-e2c18b78144f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8Z2VuZXJhbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Business","https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8YnVzaW5lc3N8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Entertainment","https://media.istockphoto.com/photos/the-musicians-were-playing-rock-music-on-stage-there-was-an-audience-picture-id1319479588?b=1&k=20&m=1319479588&s=170667a&w=0&h=bunblYyTDA_vnXu-nY4x4oa7ke6aiiZKntZ5mfr-4aM="));
        categoryRVAdapter.notifyDataSetChanged();
    }

    public void getNews(String category){
        lodingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();

        String categoryURL ="https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apikey=bcb5eac7f7fb4461b1525ac46baa935f";
        String url="https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apikey=bcb5eac7f7fb4461b1525ac46baa935f";
        String BASE_URL ="https://newsapi.org/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModel> call;

        if(category.equals("All")){
            call = retrofitAPI.getallNews(url);
        }else {
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                lodingPB.setVisibility(View.INVISIBLE);
                ArrayList<Articles> articles = newsModel.getArticles();

                for(int i=0; i<articles.size();i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription()
                    ,articles.get(i).getUrl(),articles.get(i).getUrlToImage(),articles.get(i).getContent(),articles.get(i).getSource()));

                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

                Toast.makeText(NewsActivity.this, "Failed to get News", Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }

}