package com.example.githubusersearch

import com.example.githubusersearch.domain.model.User
import com.example.githubusersearch.presentation.state.SearchState
import org.junit.Assert.*
import org.junit.Test

class SearchStateTest {

    @Test
    fun `test initial state has default values`() {
        val searchState = SearchState()

        assertFalse(searchState.isLoading)
        assertNull(searchState.user)
        assertNull(searchState.error)
    }

    @Test
    fun `test custom state with loading true`() {
        val user = User(name = "Test User", avatarUrl = "https://example.com/avatar.png")

        val searchState = SearchState(isLoading = true, user = user, error = "")

        assertTrue(searchState.isLoading)
        assertNotNull(searchState.user)
        assertEquals(user, searchState.user)
        assertEquals("", searchState.error)
    }

    @Test
    fun `test custom state with error`() {
        val errorMessage = "User not found"

        val searchState = SearchState(isLoading = false, user = null, error = errorMessage)

        assertFalse(searchState.isLoading)
        assertNull(searchState.user)
        assertEquals(errorMessage, searchState.error)
    }
}