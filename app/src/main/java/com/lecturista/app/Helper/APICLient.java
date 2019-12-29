package com.lecturista.app.Helper;

import android.content.ClipData;
import android.content.Context;

import com.google.android.gms.common.api.Batch;
import com.lecturista.app.Interface.ApiService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APICLient {

    private static Retrofit retrofit;

    public static Retrofit getApiService() {

        String baseUrl = "http://www.kualet.com/api/" ;

            if (retrofit==null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;

    }


}
