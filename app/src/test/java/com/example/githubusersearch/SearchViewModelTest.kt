package com.example.githubusersearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.githubusersearch.core.Result
import com.example.githubusersearch.domain.model.User
import com.example.githubusersearch.domain.usecase.GetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel
    private lateinit var getUserUseCase: GetUserUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        getUserUseCase = mock(GetUserUseCase::class.java)

        viewModel = SearchViewModel(getUserUseCase)

        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test initial state is correct`() {
        assertFalse(viewModel.searchState.value.isLoading)
        assertNull(viewModel.searchState.value.user)
        assertNull(viewModel.searchState.value.error)
    }

    @Test
    fun `test searchUser returns Loading state`() = runTest {
        val userId = "testUser"

        `when`(getUserUseCase.invoke(userId)).thenReturn(flowOf(Result.Loading))

        viewModel.updateSearchInput(userId)
        viewModel.searchUser()

        assertTrue(viewModel.searchState.value.isLoading)
        assertNull(viewModel.searchState.value.user)
        assertNull(viewModel.searchState.value.error)
    }

    @Test
    fun `test searchUser returns Success state`() = runTest {
        val userId = "testUser"
        val user = User(name = "Test User", avatarUrl = "https://example.com/avatar.png")

        `when`(getUserUseCase.invoke(userId)).thenReturn(flowOf(Result.Success(user)))

        viewModel.updateSearchInput(userId)
        viewModel.searchUser()

        assertFalse(viewModel.searchState.value.isLoading)
        assertEquals(user, viewModel.searchState.value.user)
        assertNull(viewModel.searchState.value.error)
    }

    @Test
    fun `test searchUser returns Error state`() = runTest {
        val userId = "testUser"
        val errorMessage = "User not found"

        `when`(getUserUseCase.invoke(userId)).thenReturn(flowOf(Result.Error(errorMessage)))

        viewModel.updateSearchInput(userId)
        viewModel.searchUser()

        assertFalse(viewModel.searchState.value.isLoading)
        assertNull(viewModel.searchState.value.user)
        assertEquals(errorMessage, viewModel.searchState.value.error)
    }

    @Test
    fun `test searchUser returns error for empty input`() = runTest {
        viewModel.updateSearchInput("")

        viewModel.searchUser()

        assertFalse(viewModel.searchState.value.isLoading)
        assertNull(viewModel.searchState.value.user)
        assertEquals("Search input cannot be empty", viewModel.searchState.value.error)
    }

    @Test
    fun `test searchUser handles non-empty input correctly`() = runTest {
        val userId = "testUser"
        val user = User(name = "Test User", avatarUrl = "https://example.com/avatar.png")

        `when`(getUserUseCase.invoke(userId)).thenReturn(flowOf(Result.Success(user)))

        viewModel.updateSearchInput(userId)
        viewModel.searchUser()

        verify(getUserUseCase).invoke(userId)

        assertFalse(viewModel.searchState.value.isLoading)
        assertEquals(user, viewModel.searchState.value.user)
        assertNull(viewModel.searchState.value.error)
    }

    @Test
    fun `test searchUser handles concurrent requests`() = runTest {
        val userId1 = "user1"
        val userId2 = "user2"
        val user1 = User(name = "User One", avatarUrl = "https://example.com/avatar1.png")
        val user2 = User(name = "User Two", avatarUrl = "https://example.com/avatar2.png")

        `when`(getUserUseCase.invoke(userId1)).thenReturn(flowOf(Result.Success(user1)))
        `when`(getUserUseCase.invoke(userId2)).thenReturn(flowOf(Result.Success(user2)))

        viewModel.updateSearchInput(userId1)
        viewModel.searchUser()

        viewModel.updateSearchInput(userId2)
        viewModel.searchUser()

        assertFalse(viewModel.searchState.value.isLoading)
        assertEquals(user2, viewModel.searchState.value.user)
        assertNull(viewModel.searchState.value.error)
    }
}