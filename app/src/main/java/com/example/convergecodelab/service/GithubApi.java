package com.example.convergecodelab.service;

import com.example.convergecodelab.model.GithubUserProfile;
import com.example.convergecodelab.model.GithubUsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubApi {
    @GET("search/users?per_page=100")
    Call<GithubUsersResponse> githubUsersList(@Query(value = "q",
            encoded=true) String query);

    @GET("users/{username}?client_id=4effd507d4ee3cec32e8&client_secret=a3c8c2f30c491aa5f00c27aa6f29a61508694f8a")
    Call<GithubUserProfile> getUserProfile(@Path("username") String username);
}
