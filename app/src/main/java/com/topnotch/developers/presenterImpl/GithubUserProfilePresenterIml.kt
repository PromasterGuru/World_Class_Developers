package com.topnotch.developers.presenterImpl

import com.topnotch.developers.interfaces.IMainContract
import com.topnotch.developers.model.GithubUserProfile

/**
 * Created by promasterguru on 17/10/2021.
 */
class GithubUserProfilePresenterIml(
    private var mainView: IMainContract.profileDetailsView?,
    private val profileContract: IMainContract.GetProfileNoticeIntractor
) : IMainContract.profilePresenter,
    IMainContract.GetProfileNoticeIntractor.OnFetchProfileFinishedListener {

    private fun isViewAttached(): Boolean {
        return mainView != null
    }

    override fun onDestroy() {
        mainView = null
    }

    override fun onRefresh(username: String) {
        if (isViewAttached()) {
            mainView!!.showProgress()
        }
        profileContract.requestProfileDataFromServer(this, username)
    }

    override fun requestProfileDataFromServer(username: String) {
        profileContract.requestProfileDataFromServer(this, username)
    }

    override fun onSuccess(githubUserProfile: GithubUserProfile) {
        if (isViewAttached()) {
            mainView!!.setDataToView(githubUserProfile)
        }
    }

    override fun onFailure(error: Any) {
        if (isViewAttached()) {
            mainView!!.onResponseFailure(error)
        }
    }
}