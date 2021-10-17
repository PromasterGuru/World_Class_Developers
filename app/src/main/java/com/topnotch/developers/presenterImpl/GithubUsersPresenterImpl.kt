package com.topnotch.developers.presenterImpl

import com.topnotch.developers.interfaces.IMainContract
import com.topnotch.developers.model.GithubUser

/**
 * Created by promasterguru on 17/10/2021.
 */
class GithubUsersPresenterImpl(
    private var mainView: IMainContract.usersListingView?,
    private val usersIntractor: IMainContract.GetUsersNoticeIntractor
) : IMainContract.usersPresenter, IMainContract.GetUsersNoticeIntractor.OnFetchUsersFinishedListener {

    override fun onDestroy() {
        mainView = null
    }

    private fun isViewAttached():Boolean{
        return mainView != null
    }
    override fun onRefresh(query: String, page: Int, per_page: Int) {
        if (isViewAttached()) {
            mainView!!.showProgress()
        }
        usersIntractor.requestUsersDataFromServer(this, query, page, per_page)
    }

    override fun requestUsersDataFromServer(query: String, page: Int, per_page: Int) {
        usersIntractor.requestUsersDataFromServer(this, query, page, per_page)
    }

    override fun onSucces(githubUsers: List<GithubUser>) {
        if (isViewAttached()) {
            mainView!!.setDataToRecyclerView(githubUsers)
            mainView!!.hideProgress()
        }
    }

    override fun onFailure(error: Any) {
        if (isViewAttached()) {
            mainView!!.onResponseFailure(error)
            mainView!!.hideProgress()
        }
    }
}