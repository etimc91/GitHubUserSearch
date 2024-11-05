package com.example.githubusersearch.data.model

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)