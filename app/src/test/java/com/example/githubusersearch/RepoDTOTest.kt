package com.example.githubusersearch

import com.example.githubusersearch.data.model.RepoDTO
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class RepoDTOTest {

    private val gson = Gson()

    @Test
    fun `RepoDTO parses correctly from JSON`() {
        val json = """
            {
                "name": "SampleRepo",
                "description": "This is a sample repository",
                "updated_at": "2023-10-01T12:34:56Z",
                "stargazers_count": 42,
                "forks": 10
            }
        """.trimIndent()

        val repoDTO = gson.fromJson(json, RepoDTO::class.java)

        assertEquals("SampleRepo", repoDTO.name)
        assertEquals("This is a sample repository", repoDTO.description)
        assertEquals("2023-10-01T12:34:56Z", repoDTO.updatedAt)
        assertEquals(42, repoDTO.stargazersCount)
        assertEquals(10, repoDTO.forksCount)
    }

    @Test
    fun `RepoDTO handles missing fields gracefully`() {
        val json = """
        {
            "name": "SampleRepo",
            "stargazers_count": 42
            // Missing description, updated_at, and forks fields
        }
    """.trimIndent()

        val repoDTO = gson.fromJson(json, RepoDTO::class.java)

        assertEquals("SampleRepo", repoDTO.name)
        assertEquals(null, repoDTO.description)
        assertEquals(null, repoDTO.updatedAt)
        assertEquals(42, repoDTO.stargazersCount)
        assertEquals(0, repoDTO.forksCount)
    }
}