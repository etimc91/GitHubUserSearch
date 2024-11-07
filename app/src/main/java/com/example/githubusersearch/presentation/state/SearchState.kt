package com.example.githubusersearch.presentation.state

import com.example.githubusersearch.domain.model.User

data class SearchState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null
)