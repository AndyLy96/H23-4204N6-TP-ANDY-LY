package com.example.tpandroid.http;
import org.kickmyb.CustomGson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtil {
    public static Service get(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(CustomGson.getIt()))
                .baseUrl("https://4204n6kickmyb-pasgratuit.azurewebsites.net")
                .build();

        Service service = retrofit.create(Service.class);
        return service;
    }

    private static OkHttpClient client() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        return client;

    }
}
