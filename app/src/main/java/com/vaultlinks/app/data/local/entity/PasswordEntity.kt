package com.vaultlinks.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val username: String,
    val passwordEncrypted: String,
    val website: String,
    val notes: String,
    val createdAt: Long,
    val updatedAt: Long
)
