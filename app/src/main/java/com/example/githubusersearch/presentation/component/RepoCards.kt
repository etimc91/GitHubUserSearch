package com.example.githubusersearch.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.githubusersearch.R
import com.example.githubusersearch.domain.model.Repo
import com.example.githubusersearch.ui.theme.AppTheme

@Composable
fun RepoSummary(repo: Repo, onItemClick: (Repo) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onItemClick(repo) }) {
        Column(modifier = Modifier.padding(8.dp)) {
            repo.name?.let { Text(it, fontWeight = FontWeight.Bold) }
            repo.description?.let { Text(it) }
        }
    }
}

@Composable
fun RepoDetail(
    name: String? = null,
    description: String? = null,
    updatedAt: String? = null,
    stargazersCount: Int? = null,
    forksCount: Int? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val fontSize = 22.sp
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            name?.let { Text(it, fontSize = fontSize, fontWeight = FontWeight.Bold) }
            description?.let { Text(it, fontSize = fontSize) }
            updatedAt?.let { Text("${stringResource(R.string.repo_updated_at)} $it", fontSize = fontSize) }
            stargazersCount?.let { Text("${stringResource(R.string.repo_star_count)} $it", fontSize = fontSize) }
            forksCount?.let { Text("${stringResource(R.string.repo_fork_count)} $it", fontSize = fontSize) }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReposPreview() {
    AppTheme {
        Column {
            RepoSummary(
                Repo(
                    name = "This is the repo name",
                    description = "This is the repo description"
                ),
                onItemClick = {}
            )
            RepoDetail(
                name = "This is the repo name",
                description = "This is the repo description",
                updatedAt = "This is a dummy date",
                stargazersCount = 467,
                forksCount = 188
            )
        }
    }
}