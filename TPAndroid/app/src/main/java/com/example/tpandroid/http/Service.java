package com.example.tpandroid.http;

import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Service {

    @POST("/api/id/signin")
    Call<SigninResponse> signin(@Body SigninRequest s);

    @POST("/api/id/signup")
    Call<SigninResponse> signup(@Body SigninRequest s);

    @POST("/api/id/signout")
    Call<String> signout();



}
