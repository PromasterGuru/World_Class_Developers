package com.topnotch.developers.presenter;

import android.util.Log;

import com.topnotch.developers.model.GithubUsers;
import com.topnotch.developers.model.GithubUsersResponse;
import com.topnotch.developers.service.GithubService;
import com.topnotch.developers.view.GithubUsersView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubPresenter {


    GithubUsersView usersView;
    GithubService githubService;

    public GithubPresenter(GithubUsersView usersView) {
        this.usersView = usersView;
        if(this.githubService == null) {
            this.githubService = new GithubService();
        }
    }

    public void getGithubUsers(final String str){
        githubService
                .getGithubApi()
                .githubUsersList(str)
                .enqueue(new Callback<GithubUsersResponse>() {
                    @Override
                    public void onResponse(Call<GithubUsersResponse> call, Response<GithubUsersResponse> response) {
                        GithubUsersResponse githubUserResponse = response.body();
                        if(githubUserResponse != null) {
                            List<GithubUsers> result =
                                    githubUserResponse.getGithubUsersList();
                            usersView.githubReadyUsers(result);
                        }
                    }

                    @Override
                    public void onFailure(Call<GithubUsersResponse> call, Throwable t) {
                        Log.d("TAG", "onFailure() returned: " + t);
                    }
                });
    }
}

