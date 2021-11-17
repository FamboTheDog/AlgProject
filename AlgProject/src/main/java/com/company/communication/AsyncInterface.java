package com.company.communication;

import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract public class AsyncInterface<T> implements Callback<T> {

    @Override
    abstract public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response);

    public void onFailure(@NotNull Call<T> var1, Throwable var2) {
        var2.printStackTrace();
    }

}
