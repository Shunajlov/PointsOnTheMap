package com.ihavenodomain.pointsonthemap.api.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConnection {
    private static final String API_ENDPOINT = "https://work.gofura.com/api/test/";

    private static final ApiConnection ourInstance = new ApiConnection();

    public static ApiConnection getInstance() {
        return ourInstance;
    }

    private ApiConnection() {
    }

    public Api getApi() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(ApiConnection.API_ENDPOINT)
                .build();
        return retrofit.create(Api.class);
    }
}
