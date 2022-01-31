package com.example.coronatracker.News;

import com.example.coronatracker.News.Models.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {

    @GET
    Call<NewsModel> getallNews(@Url String url);

    @GET
    Call<NewsModel> getNewsByCategory(@Url String url);

}
