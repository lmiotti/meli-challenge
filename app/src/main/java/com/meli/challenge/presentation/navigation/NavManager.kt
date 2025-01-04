package com.meli.challenge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.meli.challenge.presentation.ui.view.DetailScreen
import com.meli.challenge.presentation.ui.view.HomeScreen
import com.meli.challenge.presentation.viewmodel.DetailViewModel

@Composable
fun NavManager() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.Home) {
        composable<Routes.Home> {
            HomeScreen { id, name ->
                navController.navigate(Routes.Detail(id, name))
            }
        }
        composable<Routes.Detail> {
            val detail: Routes.Detail = it.toRoute()
            DetailScreen(
                viewModel = hiltViewModel(
                    creationCallback = { factory: DetailViewModel.Factory ->
                        factory.create(detail.id)
                    }
                ),
                name = detail.name,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}
