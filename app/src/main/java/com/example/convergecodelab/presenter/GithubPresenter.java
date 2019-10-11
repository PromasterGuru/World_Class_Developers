package com.example.convergecodelab.presenter;

import android.util.Log;

import com.example.convergecodelab.model.GithubUsers;
import com.example.convergecodelab.model.GithubUsersResponse;
import com.example.convergecodelab.service.GithubService;
import com.example.convergecodelab.view.GithubUsersView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubPresenter {


    GithubUsersView usersView;
    List<GithubUsers> result;
    GithubService githubService;

    public GithubPresenter(GithubUsersView usersView) {
        this.usersView = usersView;
        if(this.githubService == null) {
            this.githubService = new GithubService();
        }
    }

    public void getGithubUsers(String str){
        githubService
                .getGithubApi()
                .githubUsersList(str)
                .enqueue(new Callback<GithubUsersResponse>() {
                    @Override
                    public void onResponse(Call<GithubUsersResponse> call, Response<GithubUsersResponse> response) {
                        GithubUsersResponse githubUserResponse = response.body();
                        if(githubUserResponse != null && githubUserResponse.getGithubUsersList() != null) {
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

