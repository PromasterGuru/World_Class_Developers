package com.example.convergecodelab.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class GithubUsersTest {

    private final String userName = "PromasterGuru";
    private final String profileImage = "https://avatars0.githubusercontent.com/u/39240075?v=4";
    GithubUsers githubUsers = new GithubUsers(userName, profileImage);

    @Test
    public void getUserName() {
        assertEquals(userName, githubUsers.getUserName());
    }

    @Test
    public void getProfileImage() {
        assertEquals(profileImage, githubUsers.getProfileImage());
    }
}