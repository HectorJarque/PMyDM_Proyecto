package com.example.fbcv

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

private data class BottomItem(val route: String, val label: String, val icon: ImageVector)

private val bottomItems = listOf(
    BottomItem(Routes.HOME, "Inicio", Icons.Default.Home),
    BottomItem(Routes.PROFILE, "Ficha", Icons.Default.Person),
    BottomItem(Routes.FAVORITES, "Favoritos", Icons.Default.Star),
    BottomItem(Routes.NOTIFICATIONS, "Avisos", Icons.Default.Notifications),
    BottomItem(Routes.MENU, "Menu", Icons.Default.Menu)
)

@Composable
fun FbcvApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBars = currentRoute != Routes.SPLASH

    Scaffold(
        topBar = {
            if (showBars) {
                FbcvTopBar(
                    canGoBack = currentRoute != Routes.HOME,
                    onBack = { navController.popBackStack() },
                    onHome = { goTo(navController, Routes.HOME) }
                )
            }
        },
        bottomBar = {
            if (showBars) FbcvBottomBar(currentRoute, navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.SPLASH,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.SPLASH) {
                SplashScreen(onFinished = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                })
            }
            composable(Routes.HOME) {
                HomeScreen(
                    onProfile = { navController.navigate(Routes.PROFILE) },
                    onClubs = { navController.navigate(Routes.CLUBS) },
                    onCompetitions = { navController.navigate(Routes.COMPETITIONS) },
                    onLive = { navController.navigate(Routes.LIVE) }
                )
            }
            composable(Routes.CLUBS) { PlaceholderScreen("Clubes") }
            composable(Routes.COMPETITIONS) { PlaceholderScreen("Competiciones") }
            composable(Routes.LIVE) { PlaceholderScreen("Partidos en directo") }
            composable(Routes.PROFILE) { PlaceholderScreen("Perfil del jugador") }
            composable(Routes.FAVORITES) { PlaceholderScreen("Favoritos") }
            composable(Routes.NOTIFICATIONS) { PlaceholderScreen("Notificaciones") }
            composable(Routes.MENU) { PlaceholderScreen("Menu") }
        }
    }
}

private fun goTo(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(Routes.HOME)
        launchSingleTop = true
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FbcvTopBar(canGoBack: Boolean, onBack: () -> Unit, onHome: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text("FBCV", color = Color.White) },
        navigationIcon = {
            if (canGoBack) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                }
            }
        },
        actions = {
            IconButton(onClick = onHome) {
                Icon(Icons.Default.Home, "Inicio", tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = FbcvBlue,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        )
    )
}

@Composable
private fun FbcvBottomBar(currentRoute: String?, navController: NavHostController) {
    NavigationBar(containerColor = FbcvBlue) {
        bottomItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { goTo(navController, item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = FbcvOrange,
                    unselectedIconColor = Color.White,
                    indicatorColor = FbcvBlue
                )
            )
        }
    }
}
