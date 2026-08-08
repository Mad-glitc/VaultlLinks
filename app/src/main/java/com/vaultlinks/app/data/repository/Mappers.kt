package com.vaultlinks.app.data.repository

import com.vaultlinks.app.data.local.Converters
import com.vaultlinks.app.data.local.entity.CategoryEntity
import com.vaultlinks.app.data.local.entity.CollectionEntity
import com.vaultlinks.app.data.local.entity.LinkEntity
import com.vaultlinks.app.domain.model.Category
import com.vaultlinks.app.domain.model.Collection
import com.vaultlinks.app.domain.model.Link
import com.vaultlinks.app.domain.model.LinkPlatform
import com.vaultlinks.app.domain.model.Priority

fun LinkEntity.toDomain(): Link = Link(
    id = id,
    url = url,
    title = title,
    description = description,
    notes = notes,
    domain = domain,
    previewImageUrl = previewImageUrl,
    faviconUrl = faviconUrl,
    platform = runCatching { LinkPlatform.valueOf(platform) }.getOrDefault(LinkPlatform.GENERIC_WEB),
    categoryId = categoryId,
    collectionId = collectionId,
    tags = Converters.csvToTags(tagsCsv),
    priority = Priority.entries.getOrElse(priority) { Priority.NORMAL },
    isFavorite = isFavorite,
    isReadLater = isReadLater,
    isArchived = isArchived,
    visitCount = visitCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
    lastOpenedAt = lastOpenedAt,
    extras = Converters.jsonToExtras(extrasJson)
)

fun Link.toEntity(): LinkEntity = LinkEntity(
    id = id,
    url = url,
    title = title,
    description = description,
    notes = notes,
    domain = domain,
    previewImageUrl = previewImageUrl,
    faviconUrl = faviconUrl,
    platform = platform.name,
    categoryId = categoryId,
    collectionId = collectionId,
    tagsCsv = Converters.tagsToCsv(tags),
    priority = priority.ordinal,
    isFavorite = isFavorite,
    isReadLater = isReadLater,
    isArchived = isArchived,
    visitCount = visitCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
    lastOpenedAt = lastOpenedAt,
    extrasJson = Converters.extrasToJson(extras)
)

fun CategoryEntity.toDomain(linkCount: Int = 0): Category = Category(
    id = id,
    name = name,
    colorHex = colorHex,
    iconName = iconName,
    linkCount = linkCount
)

fun Category.toEntity(): CategoryEntity = CategoryEntity(
    id = id,
    name = name,
    colorHex = colorHex,
    iconName = iconName
)

fun CollectionEntity.toDomain(linkCount: Int = 0): Collection = Collection(
    id = id,
    name = name,
    description = description,
    parentCollectionId = parentCollectionId,
    colorHex = colorHex,
    linkCount = linkCount,
    createdAt = createdAt
)

fun Collection.toEntity(): CollectionEntity = CollectionEntity(
    id = id,
    name = name,
    description = description,
    parentCollectionId = parentCollectionId,
    colorHex = colorHex,
    createdAt = createdAt
)
