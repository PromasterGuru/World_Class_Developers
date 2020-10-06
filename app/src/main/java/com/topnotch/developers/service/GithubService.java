package com.topnotch.developers.service;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level;

public class GithubService {
    private static final int READ_TIMEOUT = 50;
    private static final int CONNECT_TIMEOUT = 50;
    private static final int WRITE_TIMEOUT = 50;

    private static final String BASE_URL = "https://api.github.com/";
    private Retrofit retrofit;

    public class RestClient {
        public OkHttpClient getClient() {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(logging).build();
            return okHttpClient;
        }
    }

    RestClient rc = new RestClient();

    public GithubApi getGithubApi() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(rc.getClient())
                    .build();
        }
        return retrofit.create(GithubApi.class);
    }
}
