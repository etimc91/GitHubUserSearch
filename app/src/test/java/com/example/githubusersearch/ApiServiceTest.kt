package com.example.githubusersearch

import com.example.githubusersearch.data.model.UserDTO
import com.example.githubusersearch.data.model.RepoDTO
import com.example.githubusersearch.data.network.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getUser returns UserDTO when response is successful`() = runBlocking {
        val mockUser = UserDTO("John Doe", "https://example.com/avatar.jpg")
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(Gson().toJson(mockUser))
        mockWebServer.enqueue(mockResponse)

        val user = apiService.getUser("john_doe")

        assertEquals("John Doe", user.name)
        assertEquals("https://example.com/avatar.jpg", user.avatarUrl)
    }

    @Test
    fun `getUserRepos returns List of RepoDTO when response is successful`() = runBlocking {
        val mockRepos = listOf(
            RepoDTO("Repo1", "Description1", "2023-10-01", 50, 5),
            RepoDTO("Repo2", "Description2", "2023-10-02", 30, 3)
        )
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(Gson().toJson(mockRepos))
        mockWebServer.enqueue(mockResponse)

        val repos = apiService.getUserRepos("john_doe")

        assertEquals(2, repos.size)
        assertEquals("Repo1", repos[0].name)
        assertEquals("Description1", repos[0].description)
        assertEquals("2023-10-01", repos[0].updatedAt)
        assertEquals(50, repos[0].stargazersCount)
        assertEquals(5, repos[0].forksCount)
    }

    @Test
    fun `getUser returns error response on 404`(): Unit = runBlocking {
        val mockResponse = MockResponse().setResponseCode(404)
        mockWebServer.enqueue(mockResponse)

        try {
            apiService.getUser("non_existing_user")
        } catch (e: Exception) {
            assert(e is HttpException)
            assertEquals(404, (e as HttpException).code())
        }
    }
}