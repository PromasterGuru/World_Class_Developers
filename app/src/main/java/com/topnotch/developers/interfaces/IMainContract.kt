package com.topnotch.developers.interfaces

import com.topnotch.developers.model.GithubUser
import com.topnotch.developers.model.GithubUserProfile

/**
 * Created by promasterguru on 17/10/2021.
 */
interface IMainContract {
    /**
     * Call when user interact with the view and other when view OnDestroy()
     * */
    interface basePresenter {
        fun onDestroy() //Called when view is destroyed
    }

    interface usersPresenter : basePresenter {
        fun onRefresh(
            query: String,
            page: Int,
            per_page: Int
        ) //Called when user swipes to refresh the UI

        fun requestUsersDataFromServer(
            query: String,
            page: Int,
            per_page: Int
        ) //Called the first time activity is created
    }

    interface profilePresenter : basePresenter {
        fun onRefresh(username: String) //Called when user swipes to refresh the UI
        fun requestProfileDataFromServer(username: String)
    }

    interface baseListView {
        fun showProgress() //Called when user needs to show progress bar
        fun hideProgress() //Called when user needs to hide progress bar
        fun onResponseFailure(error: Any) //Called to show error when network response fails or api call fails
    }

    interface usersListingView : baseListView {
        fun setDataToRecyclerView(githubUsers: List<GithubUser>) //Called when user wants to set data to the recyclerview
    }

    interface profileDetailsView : baseListView {
        fun setDataToView(githubUserProfile: GithubUserProfile)
    }

    /**
     * Intractors are classes built for fetching data from your database, web services, or any other data source.
     **/
    interface GetUsersNoticeIntractor {
        //Called when the webservices response is success or the response is failure
        interface OnFetchUsersFinishedListener {
            fun onSucces(githubUsers: List<GithubUser>)
            fun onFailure(error: Any)
        }

        fun requestUsersDataFromServer(
            onFinishedListener: OnFetchUsersFinishedListener,
            query: String,
            page: Int,
            per_page: Int
        )
    }

    interface GetProfileNoticeIntractor {
        //Called when the webservices response is success or the response is failure
        interface OnFetchProfileFinishedListener {
            fun onSuccess(githubUserProfile: GithubUserProfile)
            fun onFailure(error: Any)
        }

        fun requestProfileDataFromServer(
            onFetchProfileFinishedLister: OnFetchProfileFinishedListener,
            username: String
        )
    }
}