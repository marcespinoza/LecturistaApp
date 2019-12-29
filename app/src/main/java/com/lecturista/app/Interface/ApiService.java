package com.lecturista.app.Interface;

import com.google.gson.JsonObject;
import com.lecturista.app.POJO.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("login.php")
    Call<LoginResponse> login(@Body JsonObject user);

}
