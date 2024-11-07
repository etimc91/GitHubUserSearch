package com.example.githubusersearch

import com.example.githubusersearch.core.Result
import org.junit.Assert.assertEquals
import org.junit.Test

class ResultTest {

    @Test
    fun `Result Success contains correct data`() {
        val success = Result.Success("Success Data")
        assertEquals("Success Data", success.data)
    }

    @Test
    fun `Result Error contains correct message`() {
        val error = Result.Error("Error Message")
        assertEquals("Error Message", error.message)
    }

    @Test
    fun `Result Loading is loading state`() {
        val loading = Result.Loading
        assertEquals(null, loading.data)
        assertEquals(null, loading.message)
    }
}