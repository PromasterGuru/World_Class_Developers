package com.topnotch.developers.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GithubUsersResponseTest {

    private GithubUsersResponse usersResponse;
    private ArrayList<GithubUsers> githubUsers = new ArrayList<>();
    GithubUsers githubUsers1 = new GithubUsers("PromasterGuru", "https://avatars0.githubusercontent.com/u/39240075?v=4");
    GithubUsers githubUsers2 = new GithubUsers("k33ptoo", "https://avatars0.githubusercontent.com/u/6637970?v=4");
    GithubUsers githubUsers3 = new GithubUsers("jumaallan", "https://avatars3.githubusercontent.com/u/25085146?v=4");
    @Test
    public void getGithubUsersList() {
        githubUsers.add(githubUsers1);
        githubUsers.add(githubUsers2);
        githubUsers.add(githubUsers3);
        usersResponse = new GithubUsersResponse(githubUsers);
        assertEquals(githubUsers, usersResponse.getGithubUsersList());
    }
}
