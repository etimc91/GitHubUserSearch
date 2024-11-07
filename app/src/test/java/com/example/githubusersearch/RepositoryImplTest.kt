package com.example.githubusersearch

import com.example.githubusersearch.data.model.UserDTO
import com.example.githubusersearch.data.model.RepoDTO
import com.example.githubusersearch.data.network.ApiService
import com.example.githubusersearch.data.repository.RepositoryImpl
import com.example.githubusersearch.domain.model.User
import com.example.githubusersearch.domain.model.Repo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RepositoryImplTest {

    private lateinit var repository: RepositoryImpl
    private val mockApiService: ApiService = mockk()

    @Before
    fun setUp() {
        repository = RepositoryImpl(mockApiService)
    }

    @Test
    fun `getUser returns transformed User with repos`() = runBlocking {
        val userDTO = UserDTO(name = "SampleUser", avatarUrl = "https://example.com/avatar.jpg")
        val repoDTO = RepoDTO(
            name = "Repo1",
            description = "Description",
            updatedAt = "2021-08-01",
            stargazersCount = 42,
            forksCount = 10
        )

        coEvery { mockApiService.getUser("SampleUser") } returns userDTO
        coEvery { mockApiService.getUserRepos("SampleUser") } returns listOf(repoDTO)

        val result = repository.getUser("SampleUser")

        val expectedUser = User(
            name = "SampleUser",
            avatarUrl = "https://example.com/avatar.jpg",
            repos = listOf(
                Repo(
                    name = "Repo1",
                    description = "Description",
                    updatedAt = "2021-08-01",
                    stargazersCount = 42,
                    forksCount = 10
                )
            )
        )

        assertEquals(expectedUser, result)
    }

    @Test(expected = Exception::class)
    fun `getUser throws exception on API error`(): Unit = runBlocking {
        coEvery { mockApiService.getUser("InvalidUser") } throws Exception("User not found")

        repository.getUser("InvalidUser")
    }
}