package com.topnotch.developers.model

import com.google.gson.annotations.SerializedName

data class GithubUsersResponse(
    @SerializedName("total_count") val totalCount: Int = 0,
    @SerializedName("incomplete_results") val incompleteResults: Boolean = false,
    @SerializedName("items") val items: List<GithubUser>? = null
)