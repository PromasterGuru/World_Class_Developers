package com.topnotch.developers.service

import android.util.Log
import com.topnotch.developers.interfaces.IMainContract
import com.topnotch.developers.model.GithubUsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by promasterguru on 17/10/2021.
 */
class GetUsersNoticeIntractorImpl : IMainContract.GetUsersNoticeIntractor {

    companion object {
        val apiService = GithubApiService.getApiService()
    }

    override fun requestUsersDataFromServer(
        onFinishedListener: IMainContract.GetUsersNoticeIntractor.OnFetchUsersFinishedListener,
        query: String,
        page: Int,
        per_page: Int
    ) {
        apiService.githubUsersList(query, page, per_page)
            .enqueue(object : Callback<GithubUsersResponse?> {
                override fun onResponse(
                    call: Call<GithubUsersResponse?>,
                    response: Response<GithubUsersResponse?>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        onFinishedListener.onSucces(response.body()!!.items!!)
                    } else {
                        onFinishedListener.onFailure(response.errorBody()!!)
                    }
                }

                override fun onFailure(call: Call<GithubUsersResponse?>, t: Throwable) {
                    onFinishedListener.onFailure(t)
                }

            })
    }
}