package com.example.githubusersearch.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.githubusersearch.R
import com.example.githubusersearch.presentation.component.RepoDetail

@Composable
fun RepoDetailsScreen(
    name: String? = null,
    description: String? = null,
    updatedAt: String? = null,
    stargazersCount: Int? = null,
    forksCount: Int? = null,
    allReposForksCount: Int? = null
) {
    Column {

        allReposForksCount?.let {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(modifier = Modifier.padding(16.dp),
                    text = "${stringResource(R.string.all_repos_fork_count)} $allReposForksCount",
                    color = if (allReposForksCount > 5000) colorResource(R.color.gold) else Color.Unspecified,
                    fontSize = 24.sp,
                )
            }
        }

        RepoDetail(
            name = name,
            description = description,
            updatedAt = updatedAt,
            stargazersCount = stargazersCount,
            forksCount = forksCount,
        )
    }

}
