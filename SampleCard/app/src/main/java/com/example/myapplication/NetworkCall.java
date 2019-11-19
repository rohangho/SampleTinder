package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkCall {

    @GET("HiringTask.json")
    Call<String> call();


}
