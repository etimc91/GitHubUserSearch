package com.example.githubusersearch.data.repository

import com.example.githubusersearch.core.toUser
import com.example.githubusersearch.core.toRepo
import com.example.githubusersearch.data.network.ApiService
import com.example.githubusersearch.domain.model.User
import com.example.githubusersearch.domain.repository.Repository
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : Repository {

    override suspend fun getUser(userId: String): User {
        val user = apiService.getUser(userId).toUser()
        val repos = apiService.getUserRepos(userId).map { it.toRepo() }
        return user.copy(repos = repos)
    }
}
