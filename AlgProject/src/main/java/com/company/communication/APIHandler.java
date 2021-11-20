package com.company.communication;

import com.example.backend.db.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.scheduling.annotation.Scheduled;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class APIHandler {

    private static final String BASE_URL = "http://localhost:8080/api/v1/";

    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();


    public static <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }

}
