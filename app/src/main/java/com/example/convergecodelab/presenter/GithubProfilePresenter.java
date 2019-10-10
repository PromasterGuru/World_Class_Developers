package com.example.convergecodelab.presenter;

import android.util.Log;

import com.example.convergecodelab.model.GithubUserProfile;
import com.example.convergecodelab.service.GithubService;
import com.example.convergecodelab.view.GithubUserProfileView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubProfilePresenter {


    GithubUserProfileView profileView;
    List<GithubUserProfile> result;
    GithubService githubService;

    public GithubProfilePresenter(GithubUserProfileView profileView) {
        this.profileView = profileView;
        if(this.githubService == null) {
            this.githubService = new GithubService();
        }
    }

    public void getGithubProfiles(String username){
        githubService
                .getGithubApi()
                .getUserProfile(username)
                .enqueue(new Callback<GithubUserProfile>() {
                    @Override
                    public void onResponse(Call<GithubUserProfile> call, Response<GithubUserProfile> response) {
                        GithubUserProfile githubProfile = response.body();
                        if(githubProfile != null && githubProfile.getProfileUrl() != null) {
                            profileView.getReadyProfiles(githubProfile);
                        }
                        else{
                            Log.d("TAG", "An error occured when loading data" );
                        }
                    }

                    @Override
                    public void onFailure(Call<GithubUserProfile> call, Throwable t) {
                        Log.d("TAG", "onFailure() returned: " + t);
                    }
                });
    }
}

