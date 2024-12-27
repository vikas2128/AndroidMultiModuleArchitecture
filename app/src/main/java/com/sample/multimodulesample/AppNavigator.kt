package com.sample.multimodulesample

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sample.auth.login.LoginScreen
import com.sample.auth.login.LoginViewModel
import com.sample.domain.dto.login.response.LoginResponse
import com.sample.home.dashboard.DashboardScreen
import com.sample.multimodulesample.NavigationItem.Details
import com.sample.multimodulesample.NavigationItem.Home
import kotlinx.serialization.Serializable

@Composable
fun CreateAppNavigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                loginStateFlow = loginViewModel.loginResponse,
                loadingStateFlow = loginViewModel.loading,
                onLoginSuccess = { user ->
                    navController.navigate(
                        Details(
                            user.accessToken,
                            user.email,
                            user.firstName,
                            user.gender,
                            user.id,
                            user.image,
                            user.lastName,
                            user.refreshToken,
                            user.username
                        )
                    )
                    loginViewModel.clearState()
                },
                onLoginClick = { username, password ->
                    loginViewModel.login(username, password)
                }
            )
        }

        composable<Details> { backStackEntry ->
            val user: Details = backStackEntry.toRoute()
            DashboardScreen(user.mapToLoginResponse(),onLogout = {
                navController.popBackStack()
            })
        }
    }
}

sealed class NavigationItem {
    @Serializable
    data object Home : NavigationItem()

    @Serializable
    data class Details(
        val accessToken: String,
        val email: String,
        val firstName: String,
        val gender: String,
        val id: Int,
        val image: String,
        val lastName: String,
        val refreshToken: String,
        val username: String
    ) : NavigationItem() {
        fun mapToLoginResponse(): LoginResponse {
            return LoginResponse(
                accessToken,
                email,
                firstName,
                gender,
                id,
                image,
                lastName,
                refreshToken,
                username
            )
        }
    }
}

