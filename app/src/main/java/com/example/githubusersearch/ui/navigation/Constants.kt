package com.example.githubusersearch.ui.navigation


object NavigationRoute {
    const val USER_SEARCH = "userSearch"
    const val REPO_DETAILS =
        "repo_details?name={name}&description={description}&updatedAt={updatedAt}&stargazersCount={stargazersCount}&forks={forks}&allReposForksCount={allReposForksCount}"
}

object NavArgument {
    const val NAME = "name"
    const val DESCRIPTION = "description"
    const val UPDATED_AT = "updatedAt"
    const val STARGAZERS_COUNT = "stargazersCount"
    const val FORKS = "forks"
    const val ALL_REPOS_FORKS_COUNT = "allReposForksCount"
}
