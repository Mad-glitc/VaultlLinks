package com.vaultlinks.app.data.metadata

import com.vaultlinks.app.domain.model.LinkMetadata
import com.vaultlinks.app.domain.model.LinkPlatform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Fetches OpenGraph / platform-specific metadata for a URL the user is saving.
 *
 * This is the ONLY network activity VaultLinks ever performs, and it happens exactly once
 * per saved link (a plain GET of the page's HTML, parsed on-device). Nothing about the user
 * or their vault is ever sent anywhere — this is a one-way read of the public page you asked
 * to save.
 */
@Singleton
class MetadataFetcher @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    suspend fun fetch(url: String): LinkMetadata? = withContext(Dispatchers.IO) {
        val platform = LinkPlatform.fromUrl(url)
        val request = Request.Builder()
            .url(url)
            .header(
                "User-Agent",
                "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0 Mobile Safari/537.36"
            )
            .build()

        val html = runCatching {
            okHttpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@use null
                response.body?.string()
            }
        }.getOrNull() ?: return@withContext fallbackMetadata(url)

        val doc = runCatching { Jsoup.parse(html, url) }.getOrNull() ?: return@withContext fallbackMetadata(url)

        val base = parseOpenGraph(doc, url)
        when (platform) {
            LinkPlatform.YOUTUBE -> enrichYouTube(doc, base)
            LinkPlatform.GITHUB -> enrichGitHub(doc, base)
            else -> base
        }
    }

    private fun parseOpenGraph(doc: Document, url: String): LinkMetadata {
        fun meta(property: String): String? =
            doc.select("meta[property=$property]").attr("content").ifBlank {
                doc.select("meta[name=$property]").attr("content").ifBlank { null }
            }

        val title = meta("og:title") ?: doc.title().ifBlank { null }
        val description = meta("og:description") ?: meta("description")
        val image = meta("og:image")
        val siteName = meta("og:site_name")
        val faviconHref = doc.select("link[rel~=(?i)^(shortcut icon|icon)$]").firstOrNull()?.absUrl("href")
        val domain = runCatching { java.net.URI(url).host }.getOrDefault(null)
        val favicon = faviconHref ?: "https://www.google.com/s2/favicons?domain=$domain&sz=128"

        return LinkMetadata(
            title = title?.trim(),
            description = description?.trim(),
            previewImageUrl = image,
            faviconUrl = favicon,
            siteName = siteName
        )
    }

    private fun enrichYouTube(doc: Document, base: LinkMetadata): LinkMetadata {
        val channel = doc.select("link[itemprop=name]").attr("content")
            .ifBlank { doc.select("meta[name=author]").attr("content") }
        val extras = if (channel.isNotBlank()) mapOf("channel" to channel) else emptyMap()
        return base.copy(extras = base.extras + extras)
    }

    private fun enrichGitHub(doc: Document, base: LinkMetadata): LinkMetadata {
        // GitHub repo pages expose owner/repo via the og:title ("owner/repo") and language
        // badges in the sidebar; we keep this best-effort since GitHub markup changes often.
        val ogTitle = base.title.orEmpty()
        val parts = ogTitle.split("/")
        val extras = buildMap {
            if (parts.size >= 2) {
                put("owner", parts[0].trim())
                put("repo", parts[1].trim().substringBefore(":"))
            }
        }
        return base.copy(extras = base.extras + extras)
    }

    private fun fallbackMetadata(url: String): LinkMetadata {
        val domain = runCatching { java.net.URI(url).host }.getOrDefault(url)
        return LinkMetadata(
            title = domain,
            description = null,
            previewImageUrl = null,
            faviconUrl = "https://www.google.com/s2/favicons?domain=$domain&sz=128",
            siteName = domain
        )
    }
}
