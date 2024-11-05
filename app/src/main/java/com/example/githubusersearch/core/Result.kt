package com.example.githubusersearch.core

sealed class Result<out T>(open val data: T? = null, open val message: String? = null) {
    data class Success<out T>(override val data: T) : Result<T>(data)
    data object Loading : Result<Nothing>()
    data class Error(override val message: String, override val data: Nothing? = null) :
        Result<Nothing>(data, message)
}