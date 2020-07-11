package com.topnotch.developers.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GithubUserProfileTest {

    private final String profileUrl = "https://api.github.com/users/promasterguru";
    private final String joinDate = "2018-05-13T13:56:28Z";
    private final String repos = "25";
    private final String followers = "2";
    private final String following = "3";
    private final String company = "@andela";
    private final String bio = "Am a tech enthusiast Andela.";
    private final String gists = "0";
    GithubUserProfile profile = new GithubUserProfile(profileUrl, joinDate, repos, followers, following, company, bio, gists);


    @Test
    public void getProfileUrl() {
        assertEquals(profileUrl, profile.getProfileUrl());
    }

    @Test
    public void getJoinDate() {
        assertEquals(joinDate, profile.getJoinDate());
    }

    @Test
    public void getRepos() {
        assertEquals(repos, profile.getRepos());
    }

    @Test
    public void getFollowers() {
        assertEquals(followers, profile.getFollowers());
    }

    @Test
    public void getFollowing() {
        assertEquals(following, profile.getFollowing());
    }

    @Test
    public void getCompany() {
        assertEquals(company, profile.getCompany());
    }

    @Test
    public void getBio() {
        assertEquals(bio, profile.getBio());
    }

    @Test
    public void getGists() {
        assertEquals(gists, profile.getGists());
    }
}
