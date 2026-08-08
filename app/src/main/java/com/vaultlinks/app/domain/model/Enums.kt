package com.vaultlinks.app.domain.model

/**
 * User-assigned priority for a saved link. Stored as its ordinal in Room.
 */
enum class Priority(val label: String) {
    LOW("Low"),
    NORMAL("Normal"),
    HIGH("High"),
    URGENT("Urgent")
}

/**
 * Detected source platform, used to pick the right card layout and icon.
 */
enum class LinkPlatform(val displayName: String) {
    INSTAGRAM("Instagram"),
    YOUTUBE("YouTube"),
    GITHUB("GitHub"),
    REDDIT("Reddit"),
    TWITTER("Twitter / X"),
    LINKEDIN("LinkedIn"),
    PDF("PDF"),
    GENERIC_WEB("Website");

    companion object {
        fun fromUrl(url: String): LinkPlatform {
            val host = runCatching { java.net.URI(url).host?.lowercase() ?: "" }.getOrDefault("")
            val lowerUrl = url.lowercase()
            return when {
                host.contains("instagram.com") -> INSTAGRAM
                host.contains("youtube.com") || host.contains("youtu.be") -> YOUTUBE
                host.contains("github.com") -> GITHUB
                host.contains("reddit.com") -> REDDIT
                host.contains("twitter.com") || host.contains("x.com") -> TWITTER
                host.contains("linkedin.com") -> LINKEDIN
                lowerUrl.endsWith(".pdf") -> PDF
                else -> GENERIC_WEB
            }
        }
    }
}

enum class SortOrder(val label: String) {
    NEWEST("Newest"),
    OLDEST("Oldest"),
    RECENTLY_OPENED("Recently Opened"),
    MOST_OPENED("Most Opened"),
    PRIORITY("Priority"),
    ALPHABETICAL("A–Z")
}

enum class LinkFilter(val label: String) {
    ALL("All"),
    FAVORITES("Favorites"),
    READ_LATER("Read Later"),
    ARCHIVED("Archived"),
    UNREAD("Unread")
}

enum class ThemeMode(val label: String) {
    LIGHT("Light"),
    DARK("Dark"),
    SYSTEM("System")
}
