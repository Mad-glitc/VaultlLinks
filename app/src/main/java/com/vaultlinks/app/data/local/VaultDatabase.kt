package com.vaultlinks.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vaultlinks.app.data.local.dao.CategoryDao
import com.vaultlinks.app.data.local.dao.CollectionDao
import com.vaultlinks.app.data.local.dao.LinkDao
import com.vaultlinks.app.data.local.dao.LinkNoteDao
import com.vaultlinks.app.data.local.dao.PasswordDao
import com.vaultlinks.app.data.local.entity.CategoryEntity
import com.vaultlinks.app.data.local.entity.CollectionEntity
import com.vaultlinks.app.data.local.entity.LinkEntity
import com.vaultlinks.app.data.local.entity.LinkNoteEntity
import com.vaultlinks.app.data.local.entity.PasswordEntity

/**
 * The single source of truth for all persisted data. VaultLinks is offline-first: this is
 * the ONLY place app data lives. No remote database, no cloud sync, no external server.
 *
 * The database file itself is protected at the OS layer (app-private storage, only readable
 * by this app's UID) and additionally the "PIN Lock" / biometric gate in [com.vaultlinks.app.security]
 * controls in-app access to the data before the UI is shown.
 */
@Database(
    entities = [
        LinkEntity::class,
        CategoryEntity::class,
        CollectionEntity::class,
        LinkNoteEntity::class,
        PasswordEntity::class
    ],
    version = 3,
    exportSchema = true
)
abstract class VaultDatabase : RoomDatabase() {
    abstract fun linkDao(): LinkDao
    abstract fun categoryDao(): CategoryDao
    abstract fun collectionDao(): CollectionDao
    abstract fun linkNoteDao(): LinkNoteDao
    abstract fun passwordDao(): PasswordDao

    companion object {
        const val DATABASE_NAME = "vaultlinks.db"
    }
}
