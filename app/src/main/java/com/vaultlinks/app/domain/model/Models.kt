package com.vaultlinks.app.domain.model

/**
 * Domain-layer representation of a saved link. Decoupled from the Room entity so the
 * presentation layer never depends on persistence details.
 */
data class Link(
    val id: Long = 0L,
    val url: String,
    val title: String,
    val description: String,
    val notes: String = "",
    val domain: String,
    val previewImageUrl: String? = null,
    val faviconUrl: String? = null,
    val platform: LinkPlatform,
    val categoryId: Long? = null,
    val collectionId: Long? = null,
    val tags: List<String> = emptyList(),
    val priority: Priority = Priority.NORMAL,
    val isFavorite: Boolean = false,
    val isReadLater: Boolean = false,
    val isArchived: Boolean = false,
    val visitCount: Int = 0,
    val createdAt: Long,
    val updatedAt: Long,
    val lastOpenedAt: Long? = null,
    // Platform-specific extras (channel name, star count, etc.) kept as a flat map so
    // the schema doesn't need a new column for every platform we support.
    val extras: Map<String, String> = emptyMap()
)

data class Category(
    val id: Long = 0L,
    val name: String,
    val colorHex: String,
    val iconName: String = "folder",
    val linkCount: Int = 0
)

data class Collection(
    val id: Long = 0L,
    val name: String,
    val description: String = "",
    val parentCollectionId: Long? = null,
    val colorHex: String = "#6C5CE7",
    val linkCount: Int = 0,
    val createdAt: Long
)

data class LinkNote(
    val id: Long = 0L,
    val linkId: Long,
    val text: String,
    val createdAt: Long
)

data class Password(
    val id: Long = 0L,
    val title: String,
    val username: String,
    val passwordEncrypted: String,
    val website: String,
    val notes: String,
    val createdAt: Long,
    val updatedAt: Long
)

data class VaultStats(
    val totalLinks: Int,
    val savedToday: Int,
    val savedThisWeek: Int,
    val favoritesCount: Int,
    val readLaterCount: Int,
    val categoriesCount: Int,
    val collectionsCount: Int,
    val estimatedStorageBytes: Long,
    val topCategories: List<Pair<Category, Int>> = emptyList()
)

/** Result of parsing an arbitrary URL's HTML for OpenGraph / platform metadata. */
data class LinkMetadata(
    val title: String?,
    val description: String?,
    val previewImageUrl: String?,
    val faviconUrl: String?,
    val siteName: String?,
    val extras: Map<String, String> = emptyMap()
)
