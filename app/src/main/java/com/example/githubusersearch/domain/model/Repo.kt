package com.example.githubusersearch.domain.model

data class Repo(
    val name: String? = null,
    val description: String? = null,
    val updatedAt: String? = null,
    val stargazersCount: Int? = null,
    val forksCount: Int? = null
)
