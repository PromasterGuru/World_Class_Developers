package com.topnotch.developers.presenter;

import android.util.Log;

import com.topnotch.developers.interfaces.IGithubUserProfileView;
import com.topnotch.developers.model.GithubUserProfile;
import com.topnotch.developers.service.GithubService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubProfilePresenter {


    IGithubUserProfileView profileView;
    GithubService githubService;

    public GithubProfilePresenter(IGithubUserProfileView profileView, String username) {
        this.profileView = profileView;
        if (this.githubService == null) {
            this.githubService = new GithubService();
        }
        getGithubUserProfile(username);
    }

    public void getGithubUserProfile(String username) {
        githubService
                .getGithubApi()
                .getUserProfile(username)
                .enqueue(new Callback<GithubUserProfile>() {
                    @Override
                    public void onResponse(Call<GithubUserProfile> call, Response<GithubUserProfile> response) {
                        GithubUserProfile githubProfile = response.body();
                        if (githubProfile != null) {
                            profileView.getReadyProfiles(githubProfile);
                        } else {
                            Log.d("TAG", response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<GithubUserProfile> call, Throwable t) {
                        Log.d("TAG", "onFailure() returned: " + t);
                    }
                });
    }
}

