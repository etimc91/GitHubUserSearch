package com.example.githubusersearch.core

import com.example.githubusersearch.data.model.UserDTO
import com.example.githubusersearch.data.model.RepoDTO
import com.example.githubusersearch.domain.model.User
import com.example.githubusersearch.domain.model.Repo

fun UserDTO.toUser(): User {
    return User(
        name = name,
        avatarUrl = avatarUrl,
        repos = emptyList()
    )
}

fun RepoDTO.toRepo(): Repo {
    return Repo(
        name = name,
        description = description,
        updatedAt = updatedAt,
        stargazersCount = stargazersCount,
        forksCount = forksCount
    )
}