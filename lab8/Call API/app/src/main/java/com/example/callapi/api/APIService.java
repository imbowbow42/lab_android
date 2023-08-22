package com.example.callapi.api;

import com.example.callapi.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

    APIService apiService = new Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);


    @GET("posts")
    Call<List<User>> getListUser(@Query("userId") int userId);

}
