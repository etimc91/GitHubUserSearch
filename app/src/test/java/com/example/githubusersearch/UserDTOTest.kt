package com.example.githubusersearch

import com.example.githubusersearch.data.model.UserDTO
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test

class UserDTOTest {

    private val gson = Gson()

    @Test
    fun `UserDTO parses correctly from JSON`() {
        val json = """
            {
                "name": "SampleUser",
                "avatar_url": "https://example.com/avatar.jpg"
            }
        """.trimIndent()

        val userDTO = gson.fromJson(json, UserDTO::class.java)

        assertEquals("SampleUser", userDTO.name)
        assertEquals("https://example.com/avatar.jpg", userDTO.avatarUrl)
    }

    @Test
    fun `UserDTO handles missing fields gracefully`() {
        val json = """
        {
            "name": "SampleUser"
            // Missing avatar_url field
        }
    """.trimIndent()

        val userDTO = gson.fromJson(json, UserDTO::class.java)

        assertEquals("SampleUser", userDTO.name)
        assertEquals(null, userDTO.avatarUrl)
    }
}