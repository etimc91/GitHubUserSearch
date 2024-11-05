package com.example.githubusersearch.domain.usecase

import com.example.githubusersearch.core.Result
import com.example.githubusersearch.data.repository.RepositoryImpl
import com.example.githubusersearch.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: RepositoryImpl) {

    operator fun invoke(userId: String): Flow<Result<User>> = flow {
        emit(Result.Loading)
        try {
            val user = repository.getUser(userId)
            emit(Result.Success(user))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "An error occurred"))
        }
    }.flowOn(Dispatchers.IO)
}