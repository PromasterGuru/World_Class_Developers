package com.example.convergecodelab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GithubUsersResponse {

    //An ArrayList to hold a list of Github users
    @SerializedName("items")
    @Expose
    private final List<GithubUsers> items;

    public GithubUsersResponse(List<GithubUsers> items) {
        this.items = items;
    }

    //Getter method that returns a list of Github users
    public List<GithubUsers> getGithubUsersList() {
        return items;
    }
}
