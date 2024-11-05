package com.example.githubusersearch.domain.repository

import com.example.githubusersearch.domain.model.User

interface Repository {
    suspend fun getUser(userId: String): User
}