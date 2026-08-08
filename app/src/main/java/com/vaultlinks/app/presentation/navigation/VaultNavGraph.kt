package com.vaultlinks.app.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vaultlinks.app.presentation.screen.collections.CollectionDetailScreen
import com.vaultlinks.app.presentation.screen.collections.CollectionsScreen
import com.vaultlinks.app.presentation.screen.favorites.FavoritesScreen
import com.vaultlinks.app.presentation.screen.home.HomeScreen
import com.vaultlinks.app.presentation.screen.linkdetail.LinkDetailScreen
import com.vaultlinks.app.presentation.screen.onboarding.OnboardingScreen
import com.vaultlinks.app.presentation.screen.passwords.PasswordsScreen
import com.vaultlinks.app.presentation.screen.pinlock.PinUnlockScreen
import com.vaultlinks.app.presentation.screen.savelink.SaveLinkScreen
import com.vaultlinks.app.presentation.screen.search.SearchScreen
import com.vaultlinks.app.presentation.screen.settings.SettingsScreen
import com.vaultlinks.app.presentation.screen.splash.SplashScreen

/**
 * Root nav graph. Splash/Onboarding live outside the bottom-nav shell; everything else
 * (Home/Collections/Search/Favorites/Settings + the modal-style Save/Detail screens) lives
 * inside [MainShell], which owns the persistent bottom navigation bar.
 */
@Composable
fun VaultNavGraph(
    startDestination: String = Screen.Splash.route,
    pendingSharedUrl: String?,
    onSharedUrlConsumed: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Splash.route) {
            SplashScreen(onNavigateNext = { onboardingDone, requiresLock ->
                val next = when {
                    !onboardingDone -> Screen.Onboarding.route
                    requiresLock -> Screen.PinUnlock.route
                    else -> Screen.Home.route
                }
                navController.navigate(next) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(onFinished = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Onboarding.route) { inclusive = true }
                }
            })
        }

        composable(Screen.PinUnlock.route) {
            PinUnlockScreen(onUnlocked = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.PinUnlock.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Home.route) {
            MainShell(navController = navController, currentRoute = Screen.Home.route) {
                HomeScreen(
                    onLinkClick = { id -> navController.navigate(Screen.LinkDetail.build(id)) },
                    onSaveClick = { navController.navigate(Screen.SaveLink.build()) },
                    onCategoryClick = { /* future: category-filtered link list */ },
                    onCollectionClick = { id -> navController.navigate(Screen.CollectionDetail.build(id)) }
                )
            }
        }

        composable(Screen.Collections.route) {
            MainShell(navController = navController, currentRoute = Screen.Collections.route) {
                CollectionsScreen(onCollectionClick = { id -> navController.navigate(Screen.CollectionDetail.build(id)) })
            }
        }

        composable(Screen.Search.route) {
            MainShell(navController = navController, currentRoute = Screen.Search.route) {
                SearchScreen(onLinkClick = { id -> navController.navigate(Screen.LinkDetail.build(id)) })
            }
        }

        composable(Screen.Favorites.route) {
            MainShell(navController = navController, currentRoute = Screen.Favorites.route) {
                FavoritesScreen(onLinkClick = { id -> navController.navigate(Screen.LinkDetail.build(id)) })
            }
        }

        composable(Screen.Passwords.route) {
            MainShell(navController = navController, currentRoute = Screen.Passwords.route) {
                PasswordsScreen()
            }
        }

        composable(Screen.Settings.route) {
            MainShell(navController = navController, currentRoute = Screen.Settings.route) {
                SettingsScreen()
            }
        }

        composable(
            route = Screen.SaveLink.route,
            arguments = listOf(
                navArgument("url") { defaultValue = "" },
                navArgument("shared") { defaultValue = "false" }
            )
        ) { backStackEntry ->
            val encodedUrl = backStackEntry.arguments?.getString("url").orEmpty()
            val url = if (encodedUrl.isBlank()) pendingSharedUrl else java.net.URLDecoder.decode(encodedUrl, "UTF-8")
            SaveLinkScreen(
                prefillUrl = url,
                onDismiss = { navController.popBackStack(); onSharedUrlConsumed() },
                onSaved = { navController.popBackStack(); onSharedUrlConsumed() }
            )
        }

        composable(
            route = Screen.LinkDetail.route,
            arguments = listOf(navArgument("linkId") { type = androidx.navigation.NavType.LongType })
        ) {
            LinkDetailScreen(onBack = { navController.popBackStack() })
        }

        composable(
            route = Screen.CollectionDetail.route,
            arguments = listOf(navArgument("collectionId") { type = androidx.navigation.NavType.LongType })
        ) {
            CollectionDetailScreen(
                onBack = { navController.popBackStack() },
                onLinkClick = { id -> navController.navigate(Screen.LinkDetail.build(id)) }
            )
        }
    }
}

@Composable
private fun MainShell(
    navController: androidx.navigation.NavHostController,
    currentRoute: String,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Screen.bottomNavItems.forEach { screen ->
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(iconFor(screen, selected), contentDescription = labelFor(screen)) },
                        label = { Text(labelFor(screen)) }
                    )
                }
            }
        }
    ) { padding ->
        androidx.compose.foundation.layout.Box(modifier = Modifier.padding(bottom = padding.calculateBottomPadding())) {
            content()
        }
    }
}

private fun labelFor(screen: Screen) = when (screen) {
    Screen.Home -> "Home"
    Screen.Collections -> "Collections"
    Screen.Search -> "Search"
    Screen.Favorites -> "Favorites"
    Screen.Passwords -> "Passwords"
    Screen.Settings -> "Settings"
    else -> ""
}

private fun iconFor(screen: Screen, selected: Boolean) = when (screen) {
    Screen.Home -> if (selected) Icons.Filled.Home else Icons.Outlined.Home
    Screen.Collections -> if (selected) Icons.Filled.Folder else Icons.Outlined.Folder
    Screen.Search -> if (selected) Icons.Filled.Search else Icons.Outlined.Search
    Screen.Favorites -> if (selected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
    Screen.Passwords -> if (selected) Icons.Filled.Lock else Icons.Outlined.Lock
    Screen.Settings -> if (selected) Icons.Filled.Settings else Icons.Outlined.Settings
    else -> Icons.Filled.Home
}
