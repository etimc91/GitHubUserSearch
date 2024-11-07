package com.example.githubusersearch

import com.example.githubusersearch.core.toRepo
import com.example.githubusersearch.core.toUser
import com.example.githubusersearch.data.model.RepoDTO
import com.example.githubusersearch.data.model.UserDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class MapperTest {

    @Test
    fun `UserDTO toUser maps correctly`() {
        val userDTO = UserDTO(
            name = "test_user",
            avatarUrl = "https://example.com/avatar.png"
        )

        val user = userDTO.toUser()

        assertEquals("test_user", user.name)
        assertEquals("https://example.com/avatar.png", user.avatarUrl)
    }

    @Test
    fun `RepoDTO toRepo maps correctly`() {
        val repoDTO = RepoDTO(
            name = "test_repo",
            description = "Test repository",
            updatedAt = "2023-10-10",
            stargazersCount = 50,
            forksCount = 10
        )

        val repo = repoDTO.toRepo()

        assertEquals("test_repo", repo.name)
        assertEquals("Test repository", repo.description)
        assertEquals("2023-10-10", repo.updatedAt)
        assertEquals(50, repo.stargazersCount)
        assertEquals(10, repo.forksCount)
    }
}