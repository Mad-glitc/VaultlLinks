package com.vaultlinks.app.domain.usecase

import com.vaultlinks.app.data.metadata.MetadataFetcher
import com.vaultlinks.app.domain.model.Link
import com.vaultlinks.app.domain.model.LinkPlatform
import com.vaultlinks.app.domain.model.VaultStats
import com.vaultlinks.app.domain.repository.LinkRepository
import javax.inject.Inject

/**
 * Saves a new link. If metadata (title/description/preview image) hasn't been
 * resolved yet, kicks off a fetch and returns the result so callers (e.g. the
 * Save screen or the share-intent flow) can update the UI immediately, while a
 * WorkManager job separately guarantees the fetch completes even if the app closes.
 */
class SaveLinkUseCase @Inject constructor(
    private val repository: LinkRepository,
    private val metadataFetcher: MetadataFetcher
) {
    suspend operator fun invoke(
        url: String,
        title: String? = null,
        description: String? = null,
        notes: String = "",
        categoryId: Long? = null,
        collectionId: Long? = null,
        tags: List<String> = emptyList(),
        priority: com.vaultlinks.app.domain.model.Priority = com.vaultlinks.app.domain.model.Priority.NORMAL,
        fetchMetadataIfMissing: Boolean = true
    ): Long {
        val normalizedUrl = url.trim()
        val domain = runCatching { java.net.URI(normalizedUrl).host ?: normalizedUrl }.getOrDefault(normalizedUrl)
        val platform = LinkPlatform.fromUrl(normalizedUrl)

        var resolvedTitle = title.orEmpty()
        var resolvedDescription = description.orEmpty()
        var previewImage: String? = null
        var favicon: String? = null
        var extras: Map<String, String> = emptyMap()

        if (fetchMetadataIfMissing && (title.isNullOrBlank() || description.isNullOrBlank())) {
            val meta = runCatching { metadataFetcher.fetch(normalizedUrl) }.getOrNull()
            if (meta != null) {
                if (resolvedTitle.isBlank()) resolvedTitle = meta.title ?: domain
                if (resolvedDescription.isBlank()) resolvedDescription = meta.description.orEmpty()
                previewImage = meta.previewImageUrl
                favicon = meta.faviconUrl
                extras = meta.extras
            }
        }
        if (resolvedTitle.isBlank()) resolvedTitle = domain

        val now = System.currentTimeMillis()
        val link = Link(
            url = normalizedUrl,
            title = resolvedTitle,
            description = resolvedDescription,
            notes = notes,
            domain = domain,
            previewImageUrl = previewImage,
            faviconUrl = favicon ?: "https://www.google.com/s2/favicons?domain=$domain&sz=128",
            platform = platform,
            categoryId = categoryId,
            collectionId = collectionId,
            tags = tags,
            priority = priority,
            createdAt = now,
            updatedAt = now,
            extras = extras
        )
        return repository.saveLink(link)
    }
}

class GetLinkUseCase @Inject constructor(private val repository: LinkRepository) {
    suspend operator fun invoke(id: Long): Link? = repository.getLink(id)
}

class UpdateLinkUseCase @Inject constructor(private val repository: LinkRepository) {
    suspend operator fun invoke(link: Link) = repository.updateLink(link)
}

class DeleteLinkUseCase @Inject constructor(private val repository: LinkRepository) {
    suspend operator fun invoke(link: Link) = repository.deleteLink(link)
}

class ToggleFavoriteUseCase @Inject constructor(private val repository: LinkRepository) {
    suspend operator fun invoke(id: Long, isFavorite: Boolean) = repository.toggleFavorite(id, isFavorite)
}

class ToggleReadLaterUseCase @Inject constructor(private val repository: LinkRepository) {
    suspend operator fun invoke(id: Long, isReadLater: Boolean) = repository.toggleReadLater(id, isReadLater)
}

class ToggleArchivedUseCase @Inject constructor(private val repository: LinkRepository) {
    suspend operator fun invoke(id: Long, isArchived: Boolean) = repository.toggleArchived(id, isArchived)
}

class MarkOpenedUseCase @Inject constructor(private val repository: LinkRepository) {
    suspend operator fun invoke(id: Long) = repository.markOpened(id)
}

class SearchLinksUseCase @Inject constructor(private val repository: LinkRepository) {
    operator fun invoke(query: String) = repository.search(query)
}

class GetStatsUseCase @Inject constructor(private val repository: LinkRepository) {
    suspend operator fun invoke(): VaultStats = repository.getStats()
}
