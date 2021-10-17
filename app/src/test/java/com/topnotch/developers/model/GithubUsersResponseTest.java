package com.topnotch.developers.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GithubUsersResponseTest {

    @Test
    public void getGithubUserList() {
        ArrayList<GithubUser> githubUsers = new ArrayList<>(){{
            add(new GithubUser("PromasterGuru", "https://avatars0.githubusercontent.com/u/39240075?v=4"));
            add(new GithubUser("k33ptoo", "https://avatars0.githubusercontent.com/u/6637970?v=4"));
            add(new GithubUser("jumaallan", "https://avatars3.githubusercontent.com/u/25085146?v=4"));
        }};
        assertEquals(githubUsers, new GithubUsersResponse(githubUsers).getGithubUsers());
    }
}
