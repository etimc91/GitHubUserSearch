package com.example.githubusersearch.data.network

import com.example.githubusersearch.data.model.UserDTO
import com.example.githubusersearch.data.model.RepoDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): UserDTO

    @GET("/users/{userId}/repos")
    suspend fun getUserRepos(@Path("userId") userId: String): List<RepoDTO>
}