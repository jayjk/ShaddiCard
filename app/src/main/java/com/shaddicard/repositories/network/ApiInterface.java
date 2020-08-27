package com.shaddicard.repositories.network;


import com.shaddicard.model.Results;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface ApiInterface {


    @Headers({"Content-Type: application/json"})
    @GET("api/?results=10")
    Call<Results> getData();
}
