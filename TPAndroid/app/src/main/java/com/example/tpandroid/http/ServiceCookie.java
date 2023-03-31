package com.example.tpandroid.http;

import org.kickmyb.transfer.AddTaskRequest;
import org.kickmyb.transfer.HomeItemResponse;
import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.TaskDetailResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceCookie {

    @GET("exos/cookie/echo")
    Call<String> cookieEcho();

    @POST("/api/id/signin")
    Call<SigninResponse> signin(@Body SigninRequest s);

    @POST("/api/id/signup")
    Call<SigninResponse> signup(@Body SigninRequest s);

    @POST("/api/id/signout")
    Call<String> signout();

    @POST("/api/add")
    Call<String> addtask(@Body AddTaskRequest r);

    @GET("/api/home")
    Call<List<HomeItemResponse>> acceuil();

    @GET("/api/detail/{id}")
    Call<TaskDetailResponse> taskDetail(@Path("id") Long numberId);

    @GET("/api/progress/{id}/{valeur}")
    Call<String> taskProgress(@Path("id") Long taskId, @Path("valeur") int value);

}
