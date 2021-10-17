package com.topnotch.developers.service

import com.topnotch.developers.BuildConfig
import com.topnotch.developers.model.GithubUserProfile
import com.topnotch.developers.model.GithubUsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by promasterguru on 17/10/2021.
 */
interface GithubApi {
    @GET("search/users?client_id=" + BuildConfig.CLIENT_ID + "&client_secret=" + BuildConfig.CLIENT_SECRET)
    fun githubUsersList(
        @Query("q") query: String?,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Call<GithubUsersResponse?>

    @GET("users/{username}?client_id=" + BuildConfig.CLIENT_ID + "&client_secret=" + BuildConfig.CLIENT_SECRET)
    fun getUserProfile(@Path("username") username: String?): Call<GithubUserProfile?>
}