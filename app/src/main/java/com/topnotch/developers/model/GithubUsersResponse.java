package com.topnotch.developers.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GithubUsersResponse {

    @SerializedName("total_count")
    public Integer totalCount;
    @SerializedName("incomplete_results")
    public Boolean incompleteResults;
    @SerializedName("items")
    public List<GithubUser> githubUsers = null;

    public Integer getTotalCount() {
        return totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public List<GithubUser> getGithubUsers() {
        return githubUsers;
    }
}
