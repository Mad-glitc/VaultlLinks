package com.vaultlinks.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "link_notes",
    foreignKeys = [
        ForeignKey(
            entity = LinkEntity::class,
            parentColumns = ["id"],
            childColumns = ["linkId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("linkId")]
)
data class LinkNoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val linkId: Long,
    val text: String,
    val createdAt: Long
)
