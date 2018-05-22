package com.example.sidrajawaid.demofirebase.RetrofitFiles;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("top-headlines?sources=google-news&apiKey=5d74a26870ab4abe8e7335d0602e7d88")
    //Call<Article> getArticle();
    //Call<List<Example>> getArticle();
    Call<Example> getArticle();


}
