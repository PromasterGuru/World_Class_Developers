package com.topnotch.developers.model;

import com.google.gson.annotations.SerializedName;

public class GithubUserProfile {

    @SerializedName("html_url")
    private final String profileUrl;

    @SerializedName("created_at")
    private final String joinDate;

    @SerializedName("public_repos")
    private final String repos;

    @SerializedName("followers")
    private final String followers;

    @SerializedName("following")
    private final String following;

    @SerializedName("company")
    private final String company;

    @SerializedName("bio")
    private final String bio;

    @SerializedName("public_gists")
    private final String gists;

    public GithubUserProfile(String profileUrl, String joinDate, String repos, String followers, String following, String company, String bio, String gists) {
        this.profileUrl = profileUrl;
        this.joinDate = joinDate;
        this.repos = repos;
        this.followers = followers;
        this.following = following;
        this.company = company;
        this.bio = bio;
        this.gists = gists;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public String getRepos() {
        return repos;
    }

    public String getFollowers() {
        return followers;
    }

    public String getFollowing() {
        return following;
    }

    public String getCompany() {
        return company;
    }

    public String getBio() {
        return bio;
    }

    public String getGists() {
        return gists;
    }

}
