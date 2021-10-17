package com.topnotch.developers.service

import com.topnotch.developers.interfaces.IMainContract
import com.topnotch.developers.model.GithubUserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by promasterguru on 17/10/2021.
 */
class GetProfileNoticeIntractorImpl : IMainContract.GetProfileNoticeIntractor {
    companion object {
        val apiService = GithubApiService.getApiService();
    }

    override fun requestProfileDataFromServer(
        onFetchProfileFinishedLister: IMainContract.GetProfileNoticeIntractor.OnFetchProfileFinishedListener,
        username: String
    ) {
        apiService.getUserProfile(username)
            .enqueue(object : Callback<GithubUserProfile?> {
                override fun onResponse(
                    call: Call<GithubUserProfile?>,
                    response: Response<GithubUserProfile?>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        onFetchProfileFinishedLister.onSuccess(response.body()!!)
                    } else {
                        onFetchProfileFinishedLister.onFailure(response.errorBody()!!)
                    }
                }

                override fun onFailure(call: Call<GithubUserProfile?>, t: Throwable) {
                    onFetchProfileFinishedLister.onFailure(t)
                }
            })
    }
}