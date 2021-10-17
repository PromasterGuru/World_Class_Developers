package com.topnotch.developers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.topnotch.developers.R
import com.topnotch.developers.interfaces.RecyclerItemClickListener
import com.topnotch.developers.model.GithubUser
import com.topnotch.developers.viewholders.GithubUsersHolder

/**
 * Created by promasterguru on 17/10/2021.
 */
class GithubAdapter(private val onItemClickListener: RecyclerItemClickListener) :
    RecyclerView.Adapter<GithubUsersHolder>() {
    private var githubUsers = mutableListOf<GithubUser>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUsersHolder {
        return GithubUsersHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_users, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GithubUsersHolder, position: Int) {
        holder.bind(onItemClickListener, githubUsers[position])
    }

    override fun getItemCount(): Int {
        return githubUsers.size
    }

    fun addUsers(users: List<GithubUser>) {
        githubUsers.addAll(users)
        notifyItemRangeChanged(itemCount - users.size, users.size)
        notifyItemRangeChanged(itemCount - users.size, users.size)
    }

    fun searchResult(users: List<GithubUser>) {
        githubUsers.clear()
        notifyItemRangeChanged(itemCount - users.size, users.size)
        notifyItemRangeChanged(itemCount - users.size, users.size)
    }

    fun clearUsers() {
        val count = itemCount
        githubUsers.clear()
        notifyItemRangeRemoved(0, count)
        notifyItemRangeChanged(0, itemCount)
    }
}
