package com.vaultlinks.app.data.local

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Room doesn't need @TypeConverter registrations here because tags/extras are stored as
 * plain CSV / JSON text columns directly on [com.vaultlinks.app.data.local.entity.LinkEntity].
 * These helpers centralize the (de)serialization so it isn't duplicated across mappers.
 */
object Converters {
    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    fun tagsToCsv(tags: List<String>): String = tags.joinToString(",") { it.trim() }

    fun csvToTags(csv: String): List<String> =
        if (csv.isBlank()) emptyList() else csv.split(",").map { it.trim() }.filter { it.isNotEmpty() }

    fun extrasToJson(extras: Map<String, String>): String = json.encodeToString(extras)

    fun jsonToExtras(jsonStr: String): Map<String, String> =
        runCatching { json.decodeFromString<Map<String, String>>(jsonStr) }.getOrDefault(emptyMap())
}
