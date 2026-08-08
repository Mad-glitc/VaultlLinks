package com.vaultlinks.app.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")

    data object Home : Screen("home")
    data object Collections : Screen("collections")
    data object Search : Screen("search")
    data object Favorites : Screen("favorites")
    data object Settings : Screen("settings")

    data object SaveLink : Screen("save_link?url={url}&shared={shared}") {
        fun build(url: String? = null, shared: Boolean = false): String {
            val encoded = url?.let { java.net.URLEncoder.encode(it, "UTF-8") } ?: ""
            return "save_link?url=$encoded&shared=$shared"
        }
    }

    data object LinkDetail : Screen("link_detail/{linkId}") {
        fun build(linkId: Long) = "link_detail/$linkId"
    }

    data object CollectionDetail : Screen("collection_detail/{collectionId}") {
        fun build(collectionId: Long) = "collection_detail/$collectionId"
    }

    data object PinSetup : Screen("pin_setup")
    data object PinUnlock : Screen("pin_unlock")

    data object Passwords : Screen("passwords")

    companion object {
        /** Bottom-nav destinations, in display order. */
        val bottomNavItems = listOf(Home, Collections, Search, Passwords, Settings)
    }
}
