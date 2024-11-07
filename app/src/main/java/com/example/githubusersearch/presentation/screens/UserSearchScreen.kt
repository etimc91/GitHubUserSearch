package com.example.githubusersearch.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.githubusersearch.R
import com.example.githubusersearch.domain.model.User
import com.example.githubusersearch.presentation.component.RepoSummary
import com.example.githubusersearch.SearchViewModel

@Composable
fun UserSearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchState = viewModel.searchState.collectAsStateWithLifecycle()
    val inputState = viewModel.inputState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(16.dp)) {
        SearchInputRow(inputState.value, onSearchClick = {
            viewModel.searchUser()
        }) {
            viewModel.updateSearchInput(it)
        }

        when {
            searchState.value.isLoading -> LoadingIndicator()
            searchState.value.error?.isNotEmpty() == true -> ErrorText(searchState.value.error!!)
            else -> searchState.value.user?.let { user ->
                UserInfo(
                    navController, user
                )
            }
        }
    }
}

@Composable
private fun SearchInputRow(
    input: String,
    onSearchClick: () -> Unit,
    onInputChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            value = input,
            onValueChange = onInputChange,
            label = { Text(stringResource(R.string.search_input)) }
        )
        Button(onClick = onSearchClick) {
            Text(stringResource(R.string.search_button))
        }
    }
}

@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(50.dp))
    }
}

@Composable
private fun ErrorText(errorMessage: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = errorMessage)
    }
}

@Composable
private fun UserInfo(navController: NavController, user: User) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (user.avatarUrl != null) {
            Image(
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp),
                painter = rememberAsyncImagePainter(user.avatarUrl),
                contentDescription = null
            )
        }
        if (user.name != null) Text(
            modifier = Modifier.padding(8.dp),
            text = user.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        if (user.repos != null) {

            val allReposForksCount = user.repos.sumOf { it.forksCount ?: 0 }

            LazyColumn {
                items(user.repos) { repo ->
                    RepoSummary(repo, onItemClick = {
                        navController.navigate(
                            "repo_details?name=${repo.name}&description=${repo.description}" +
                                    "&updatedAt=${repo.updatedAt}&stargazersCount=${repo.stargazersCount}&forks=${repo.forksCount}&allReposForksCount=${allReposForksCount}"
                        )
                    })
                }
            }
        }
    }
}