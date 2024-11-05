package com.example.githubusersearch.domain.model

data class User (
    val name : String? = null,
    val avatarUrl : String? = null,
    val repos : List<Repo>? = null
)