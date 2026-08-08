package com.vaultlinks.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "links",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = CollectionEntity::class,
            parentColumns = ["id"],
            childColumns = ["collectionId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("categoryId"),
        Index("collectionId"),
        Index("isFavorite"),
        Index("isReadLater"),
        Index("isArchived"),
        Index("createdAt")
    ]
)
data class LinkEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val url: String,
    val title: String,
    val description: String,
    val notes: String,
    val domain: String,
    val previewImageUrl: String?,
    val faviconUrl: String?,
    val platform: String,
    val categoryId: Long?,
    val collectionId: Long?,
    @ColumnInfo(defaultValue = "") val tagsCsv: String = "",
    val priority: Int,
    val isFavorite: Boolean,
    val isReadLater: Boolean,
    val isArchived: Boolean,
    val visitCount: Int,
    val createdAt: Long,
    val updatedAt: Long,
    val lastOpenedAt: Long?,
    @ColumnInfo(defaultValue = "{}") val extrasJson: String = "{}"
)
