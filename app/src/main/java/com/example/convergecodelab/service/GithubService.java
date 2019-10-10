package com.example.convergecodelab.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubService {

    private static final String BASE_URL="https://api.github.com/";
    private Retrofit retrofit;

    public GithubApi getGithubApi() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(GithubApi.class);
    }
}
