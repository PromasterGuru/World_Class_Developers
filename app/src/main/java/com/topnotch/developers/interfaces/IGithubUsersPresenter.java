package com.topnotch.developers.interfaces;

import com.topnotch.developers.model.GithubUser;

public interface IGithubUsersPresenter {
    void loadGithubUsers(String keyword, int page, int per_page);
    void displayClickedUser(GithubUser githubUser);
}
