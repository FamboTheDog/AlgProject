package com.company.communication;

import com.example.backend.db.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface APICalls {

    @GET("getAll")
    Call<List<User>> getUsers();

    @POST("register")
    Call<User> register(@Body User user);

    @POST("login")
    Call<User> login(@Body User user);

}
