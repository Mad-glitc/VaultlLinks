package com.vaultlinks.app.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vaultlinks.app.data.local.VaultDatabase
import com.vaultlinks.app.data.local.dao.CategoryDao
import com.vaultlinks.app.data.local.dao.CollectionDao
import com.vaultlinks.app.data.local.dao.LinkDao
import com.vaultlinks.app.security.DatabaseKeyProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideVaultDatabase(
        @ApplicationContext context: Context,
        keyProvider: DatabaseKeyProvider
    ): VaultDatabase {
        // Load SQLCipher's native library once, then hand Room a SupportSQLiteOpenHelper.Factory
        // that transparently encrypts/decrypts the database file with the Keystore-backed
        // passphrase from DatabaseKeyProvider. From Room's perspective nothing else changes —
        // all DAOs, queries, and Paging 3 sources work exactly as they would on a plain
        // SQLite database.
        SQLiteDatabase.loadLibs(context)
        val passphrase = keyProvider.getOrCreatePassphrase()
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(context, VaultDatabase::class.java, VaultDatabase.DATABASE_NAME)
            .openHelperFactory(factory)
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            // No destructive fallback in production: schema changes must ship a real Migration
            // so a user's saved links are never silently wiped by an app update.
            .build()
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS `link_notes` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`linkId` INTEGER NOT NULL, " +
                    "`text` TEXT NOT NULL, " +
                    "`createdAt` INTEGER NOT NULL, " +
                    "FOREIGN KEY(`linkId`) REFERENCES `links`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE " +
                    ")"
            )
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_link_notes_linkId` ON `link_notes` (`linkId`)")
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS `passwords` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "`title` TEXT NOT NULL, " +
                    "`username` TEXT NOT NULL, " +
                    "`passwordEncrypted` TEXT NOT NULL, " +
                    "`website` TEXT NOT NULL, " +
                    "`notes` TEXT NOT NULL, " +
                    "`createdAt` INTEGER NOT NULL, " +
                    "`updatedAt` INTEGER NOT NULL" +
                    ")"
            )
        }
    }

    @Provides
    fun provideLinkDao(db: VaultDatabase): LinkDao = db.linkDao()

    @Provides
    fun provideCategoryDao(db: VaultDatabase): CategoryDao = db.categoryDao()

    @Provides
    fun provideCollectionDao(db: VaultDatabase): CollectionDao = db.collectionDao()

    @Provides
    fun provideLinkNoteDao(db: VaultDatabase): com.vaultlinks.app.data.local.dao.LinkNoteDao = db.linkNoteDao()

    @Provides
    fun providePasswordDao(db: VaultDatabase): com.vaultlinks.app.data.local.dao.PasswordDao = db.passwordDao()
}

