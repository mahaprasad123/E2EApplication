package com.example.e2eapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.e2eapp.presentation.screens.Dashboard
import com.example.e2eapp.presentation.screens.EmailDetails
import com.example.e2eapp.presentation.viewmodel.DashboardViewmodel
import kotlinx.serialization.Serializable

@Composable
fun InitNavigation(viewmodel: DashboardViewmodel) {
    val navController = LocalNavController.current
    NavHost(navController = navController, startDestination = Screen.Dashboard) {
        composable<Screen.Dashboard> { Dashboard(viewmodel, Modifier) }
        composable<Screen.Details> {
            val id = it.arguments?.getString("id")?.toInt()
            EmailDetails(
                id,
                modifier = Modifier,
            )
        }
    }
}

@Serializable
sealed interface Screen {
    @Serializable
    object Dashboard : Screen

    @Serializable
    class Details(
        val id: String,
    ) : Screen
}

val LocalNavController = compositionLocalOf<NavHostController> { error("no nav controller") }
