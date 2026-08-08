package com.vaultlinks.app.data.backup

import android.content.Context
import androidx.core.content.FileProvider
import com.vaultlinks.app.domain.model.Link
import com.vaultlinks.app.domain.model.LinkPlatform
import com.vaultlinks.app.domain.model.Priority
import com.vaultlinks.app.domain.repository.LinkRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Serializable
data class BackupLink(
    val url: String,
    val title: String,
    val description: String,
    val notes: String,
    val domain: String,
    val previewImageUrl: String? = null,
    val faviconUrl: String? = null,
    val platform: String,
    val tags: List<String> = emptyList(),
    val priority: String,
    val isFavorite: Boolean,
    val isReadLater: Boolean,
    val isArchived: Boolean,
    val visitCount: Int,
    val createdAt: Long,
    val updatedAt: Long,
    val lastOpenedAt: Long? = null
)

@Serializable
data class BackupPayload(
    val schemaVersion: Int = 1,
    val exportedAt: Long,
    val appVersion: String,
    val links: List<BackupLink>
)

/**
 * Produces and restores local backup files (JSON or CSV) under app-private external storage.
 * Nothing is uploaded anywhere — export just writes a file the user can then share, move to
 * their own cloud drive, or keep as a local snapshot; import reads a file the user picks.
 */
@Singleton
class BackupManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val linkRepository: LinkRepository
) {
    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true; encodeDefaults = true }

    suspend fun exportToJson(): File {
        val links = linkRepository.exportAll()
        val payload = BackupPayload(
            exportedAt = System.currentTimeMillis(),
            appVersion = "1.0.0",
            links = links.map { it.toBackup() }
        )
        val content = json.encodeToString(payload)
        return writeBackupFile("vaultlinks_backup_${timestamp()}.json", content)
    }

    suspend fun exportToCsv(): File {
        val links = linkRepository.exportAll()
        val header = "title,url,domain,category,tags,priority,favorite,readLater,archived,notes,createdAt\n"
        val rows = links.joinToString("\n") { link ->
            listOf(
                link.title, link.url, link.domain,
                link.categoryId?.toString().orEmpty(),
                link.tags.joinToString(";"),
                link.priority.name, link.isFavorite, link.isReadLater, link.isArchived,
                link.notes.replace(",", ";").replace("\n", " "), link.createdAt
            ).joinToString(",") { field -> "\"${field.toString().replace("\"", "'")}\"" }
        }
        return writeBackupFile("vaultlinks_export_${timestamp()}.csv", header + rows)
    }

    suspend fun importFromJson(file: File): Int {
        val content = file.readText()
        val payload = json.decodeFromString<BackupPayload>(content)
        val links = payload.links.map { it.toDomain() }
        linkRepository.importAll(links)
        return links.size
    }

    fun shareableUriFor(file: File) =
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

    private fun writeBackupFile(name: String, content: String): File {
        val dir = File(context.getExternalFilesDir(null), "backups").apply { mkdirs() }
        val file = File(dir, name)
        file.writeText(content)
        return file
    }

    private fun timestamp() = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
}

private fun Link.toBackup() = BackupLink(
    url = url, title = title, description = description, notes = notes, domain = domain,
    previewImageUrl = previewImageUrl, faviconUrl = faviconUrl, platform = platform.name,
    tags = tags, priority = priority.name, isFavorite = isFavorite, isReadLater = isReadLater,
    isArchived = isArchived, visitCount = visitCount, createdAt = createdAt, updatedAt = updatedAt,
    lastOpenedAt = lastOpenedAt
)

private fun BackupLink.toDomain() = Link(
    url = url, title = title, description = description, notes = notes, domain = domain,
    previewImageUrl = previewImageUrl, faviconUrl = faviconUrl,
    platform = runCatching { LinkPlatform.valueOf(platform) }.getOrDefault(LinkPlatform.GENERIC_WEB),
    tags = tags, priority = runCatching { Priority.valueOf(priority) }.getOrDefault(Priority.NORMAL),
    isFavorite = isFavorite, isReadLater = isReadLater, isArchived = isArchived,
    visitCount = visitCount, createdAt = createdAt, updatedAt = updatedAt, lastOpenedAt = lastOpenedAt
)
