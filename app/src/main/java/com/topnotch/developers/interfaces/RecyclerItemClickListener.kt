package com.topnotch.developers.interfaces

import com.topnotch.developers.model.GithubUser

/**
 * Created by promasterguru on 17/10/2021.
 */
interface RecyclerItemClickListener {
    fun onItemClicked(githubUser: GithubUser)
}