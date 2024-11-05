package com.example.githubusersearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.githubusersearch.presentation.screens.RepoDetailsScreen
import com.example.githubusersearch.presentation.screens.UserSearchScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.USER_SEARCH,
        modifier = modifier
    ) {
        composable(route = NavigationRoute.USER_SEARCH) {
            UserSearchScreen(navController)
        }
        composable(
            route = NavigationRoute.REPO_DETAILS,
            arguments = listOf(
                with(NavArgument) {
                    navArgument(NAME) { type = NavType.StringType }
                    navArgument(DESCRIPTION) { type = NavType.StringType }
                    navArgument(UPDATED_AT) { type = NavType.StringType }
                    navArgument(STARGAZERS_COUNT) { type = NavType.IntType }
                    navArgument(FORKS) { type = NavType.IntType }
                    navArgument(ALL_REPOS_FORKS_COUNT) { type = NavType.IntType }
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.let { bundle ->
                with(bundle) {
                    RepoDetailsScreen(
                        name =  getString(NavArgument.NAME),
                        description = getString(NavArgument.DESCRIPTION),
                        updatedAt = getString(NavArgument.UPDATED_AT),
                        stargazersCount = getInt(NavArgument.STARGAZERS_COUNT),
                        forksCount = getInt(NavArgument.FORKS),
                        allReposForksCount = getInt(NavArgument.ALL_REPOS_FORKS_COUNT)
                    )
                }
            }
        }
    }
}