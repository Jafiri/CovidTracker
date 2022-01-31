package com.example.coronatracker.API_State;

import com.example.coronatracker.API_State.Models.StateModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RequestInterface {

    @GET
    Call<StateModel> getStateData(@Url String url);
}
