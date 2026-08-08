package com.vaultlinks.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "collections",
    foreignKeys = [
        ForeignKey(
            entity = CollectionEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentCollectionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("parentCollectionId")]
)
data class CollectionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val description: String,
    val parentCollectionId: Long?,
    val colorHex: String,
    val createdAt: Long
)
