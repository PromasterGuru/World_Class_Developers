package com.topnotch.developers.service;

import com.topnotch.developers.BuildConfig;
import com.topnotch.developers.model.GithubUserProfile;
import com.topnotch.developers.model.GithubUsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubApi {
    @GET("search/users?client_id=" + BuildConfig.CLIENT_ID + "&client_secret=" + BuildConfig.CLIENT_SECRET + "&per_page=500")
    Call<GithubUsersResponse> githubUsersList(@Query(value = "q",
            encoded = true) String query);

    @GET("users/{username}?client_id=" + BuildConfig.CLIENT_ID + "&client_secret=" + BuildConfig.CLIENT_SECRET)
    Call<GithubUserProfile> getUserProfile(@Path("username") String username);
}
