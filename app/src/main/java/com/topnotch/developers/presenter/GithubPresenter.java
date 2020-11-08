package com.topnotch.developers.presenter;

import android.util.Log;

import com.topnotch.developers.interfaces.IGithubUsersPresenter;
import com.topnotch.developers.interfaces.IGithubUsersView;
import com.topnotch.developers.model.GithubUser;
import com.topnotch.developers.model.GithubUsersResponse;
import com.topnotch.developers.service.GithubService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubPresenter implements IGithubUsersPresenter {

    IGithubUsersView usersView;
    GithubService githubService;

    public GithubPresenter(IGithubUsersView usersView, String keyword, int page, int per_page) {
        this.usersView = usersView;
        if (this.githubService == null) {
            this.githubService = new GithubService();
        }
        loadGithubUsers(keyword, page, per_page);
    }

    @Override
    public void loadGithubUsers(String keyword, int page, int per_page) {
        githubService
                .getGithubApi()
                .githubUsersList(keyword, page, per_page)
                .enqueue(new Callback<GithubUsersResponse>() {
                    @Override
                    public void onResponse(Call<GithubUsersResponse> call, Response<GithubUsersResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<GithubUser> result = response.body().getGithubUsers();
                            usersView.githubReadyUsers(result);
                        }
                    }

                    @Override
                    public void onFailure(Call<GithubUsersResponse> call, Throwable t) {
                        Log.d("TAG", "onFailure() returned: " + t);
                    }
                });
    }

    @Override
    public void displayClickedUser(GithubUser githubUser) {
        this.usersView.displayUser(githubUser);
    }
}

