package com.example.sidrajawaid.demofirebase.RetrofitFiles;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String KEY="05b3c3742468459b9cb5de887c8f79ce";
    public static final String BASE_URL="https://newsapi.org/v2/";
    public static Retrofit retrofit;
    public static final String TAG="ApiClient";

    public static Retrofit getApiClient() {
        if(retrofit==null)
        {
            Log.d(TAG,"Retrofit is null");
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory
                    .create())
                    .build();
            Log.d(TAG,"Retrofit Client created");
        }
        return retrofit;
    }
}
