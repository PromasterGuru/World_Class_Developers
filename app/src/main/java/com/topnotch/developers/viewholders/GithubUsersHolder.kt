package com.topnotch.developers.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.topnotch.developers.R
import com.topnotch.developers.interfaces.RecyclerItemClickListener
import com.topnotch.developers.model.GithubUser
import de.hdodenhof.circleimageview.CircleImageView

class GithubUsersHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvUsername: TextView = itemView.findViewById(R.id.txtUsername)
    var circleImageView: CircleImageView = itemView.findViewById(R.id.imgUser)
    fun bind(onItemClickListener: RecyclerItemClickListener, githubUser: GithubUser) {
        tvUsername.text = githubUser.login
        Glide.with(itemView)
            .load(githubUser.avatarUrl)
            .placeholder(R.drawable.spinner)
            .error(R.mipmap.ic_launcher_round)
            .into(circleImageView)

        itemView.setOnClickListener {
            onItemClickListener.onItemClicked(githubUser)
        }
    }
}
