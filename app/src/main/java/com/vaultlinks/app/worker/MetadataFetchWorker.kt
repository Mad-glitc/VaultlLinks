package com.vaultlinks.app.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.vaultlinks.app.data.metadata.MetadataFetcher
import com.vaultlinks.app.domain.repository.LinkRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Guarantees a link's preview metadata gets fetched even if the user backgrounds the app
 * immediately after saving (e.g. via the share-intent flow, where we save first and enrich
 * second so the share sheet dismisses instantly).
 */
@HiltWorker
class MetadataFetchWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val linkRepository: LinkRepository,
    private val metadataFetcher: MetadataFetcher
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val linkId = inputData.getLong(KEY_LINK_ID, -1L)
        if (linkId == -1L) return Result.failure()

        val link = linkRepository.getLink(linkId) ?: return Result.failure()
        if (!link.title.isNullOrBlank() && !link.description.isNullOrBlank() && link.previewImageUrl != null) {
            return Result.success()
        }

        val metadata = metadataFetcher.fetch(link.url) ?: return Result.retry()
        val updated = link.copy(
            title = link.title.ifBlank { metadata.title ?: link.domain },
            description = link.description.ifBlank { metadata.description.orEmpty() },
            previewImageUrl = metadata.previewImageUrl ?: link.previewImageUrl,
            faviconUrl = metadata.faviconUrl ?: link.faviconUrl,
            extras = link.extras + metadata.extras
        )
        linkRepository.updateLink(updated)
        return Result.success()
    }

    companion object {
        const val KEY_LINK_ID = "link_id"
    }
}
