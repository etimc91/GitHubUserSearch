package com.example.githubusersearch

import com.example.githubusersearch.data.di.NetworkModule
import com.example.githubusersearch.data.network.ApiService
import com.example.githubusersearch.domain.repository.Repository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class NetworkModuleTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var apiService: ApiService

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testProvideBaseUrl() {
        val baseUrl = NetworkModule().provideBaseUrl()
        assertEquals("https://api.github.com/", baseUrl)
    }

    @Test
    fun testProvideRetrofit() {
        val retrofit = NetworkModule().provideRetrofit()
        assertNotNull(retrofit)
        assertEquals("https://api.github.com/", retrofit.baseUrl().toString())
    }

    @Test
    fun testProvideApiService() {
        assertNotNull(apiService)
    }

    @Test
    fun testProvideRepository() {
        assertNotNull(repository)
    }
}