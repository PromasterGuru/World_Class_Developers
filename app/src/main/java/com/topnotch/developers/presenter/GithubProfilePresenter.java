package com.topnotch.developers.presenter;

import android.content.Context;
import android.util.Log;

import com.topnotch.developers.model.GithubUserProfile;
import com.topnotch.developers.service.GithubService;
import com.topnotch.developers.view.GithubUserProfileView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubProfilePresenter {


    GithubUserProfileView profileView;
    List<GithubUserProfile> result;
    GithubService githubService;
    SweetAlertDialog dialog;
    Context context;

    public GithubProfilePresenter(Context context, GithubUserProfileView profileView) {
        this.profileView = profileView;
        this.context = context;
        if(this.githubService == null) {
            this.githubService = new GithubService();
        }
    }

    public void getGithubProfiles(String username){
        loadingDeveloperDialog(username);
        githubService
                .getGithubApi()
                .getUserProfile(username)
                .enqueue(new Callback<GithubUserProfile>() {
                    @Override
                    public void onResponse(Call<GithubUserProfile> call, Response<GithubUserProfile> response) {
                        GithubUserProfile githubProfile = response.body();
                        dialog.dismissWithAnimation();
                        if(githubProfile != null && githubProfile.getProfileUrl() != null) {
                            profileView.getReadyProfiles(githubProfile);
                        }
                        else{
                            Log.d("TAG", "An error occured when loading data" );
                        }
                    }

                    @Override
                    public void onFailure(Call<GithubUserProfile> call, Throwable t) {
                        dialog.dismissWithAnimation();
                        Log.d("TAG", "onFailure() returned: " + t);
                    }
                });
    }


    private void loadingDeveloperDialog(String username){
        dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText("Fetching "+username+" profile");
        dialog.setContentText("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
    }
}

