package com.topnotch.developers.interfaces;

import com.topnotch.developers.model.GithubUser;

import java.util.List;

public interface IGithubUsersView {
    void githubReadyUsers(List<GithubUser> githubUsers);
    void displayUser(GithubUser githubUser);
}

