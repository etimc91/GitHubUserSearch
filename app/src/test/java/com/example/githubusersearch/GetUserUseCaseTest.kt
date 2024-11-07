package com.example.githubusersearch

import com.example.githubusersearch.core.Result
import com.example.githubusersearch.data.repository.RepositoryImpl
import com.example.githubusersearch.domain.model.User
import com.example.githubusersearch.domain.usecase.GetUserUseCase
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetUserUseCaseTest {

    private lateinit var getUserUseCase: GetUserUseCase
    private val mockRepository: RepositoryImpl = mock(RepositoryImpl::class.java)

    @Before
    fun setUp() {
        getUserUseCase = GetUserUseCase(mockRepository)
    }

    @Test
    fun `test getUser returns Loading initially`() = runTest {
        val userId = "testUser"

        val result = getUserUseCase(userId).toList()

        assert(result.isNotEmpty())
        assert(result[0] is Result.Loading)
    }

    @Test
    fun `test getUser returns Success when repository succeeds`() = runTest {
        val userId = "testUser"
        val expectedUser = User(name = "Test User", avatarUrl = "https://example.com/avatar.png")
        `when`(mockRepository.getUser(userId)).thenReturn(expectedUser)

        val result = getUserUseCase(userId).toList()

        assert(result[0] is Result.Loading)
        assert(result[1] is Result.Success)
        assert((result[1] as Result.Success).data == expectedUser)
    }

    @Test
    fun `test getUser returns Error when repository fails`() = runTest {
        val userId = "testUser"
        val errorMessage = "Network error"
        `when`(mockRepository.getUser(userId)).thenThrow(RuntimeException(errorMessage))

        val result = getUserUseCase(userId).toList()

        assert(result[0] is Result.Loading)
        assert(result[1] is Result.Error)
        assert((result[1] as Result.Error).message == errorMessage)
    }
}