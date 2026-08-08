package com.vaultlinks.app.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vaultlinks.app.data.local.entity.LinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(link: LinkEntity): Long

    @Update
    suspend fun update(link: LinkEntity)

    @Delete
    suspend fun delete(link: LinkEntity)

    @Query("DELETE FROM links WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM links WHERE id = :id")
    suspend fun getById(id: Long): LinkEntity?

    @Query("SELECT * FROM links WHERE id = :id")
    fun observeById(id: Long): Flow<LinkEntity?>

    @Query("SELECT * FROM links WHERE isArchived = 0 ORDER BY createdAt DESC LIMIT :limit")
    fun observeRecent(limit: Int = 10): Flow<List<LinkEntity>>

    @Query("SELECT * FROM links WHERE isFavorite = 1 AND isArchived = 0 ORDER BY updatedAt DESC LIMIT :limit")
    fun observePinned(limit: Int = 8): Flow<List<LinkEntity>>

    @Query("SELECT * FROM links WHERE lastOpenedAt IS NOT NULL AND isArchived = 0 ORDER BY lastOpenedAt DESC LIMIT :limit")
    fun observeRecentlyViewed(limit: Int = 10): Flow<List<LinkEntity>>

    @Query(
        """
        SELECT * FROM links
        WHERE isArchived = 0
        AND (:categoryId IS NULL OR categoryId = :categoryId)
        AND (:collectionId IS NULL OR collectionId = :collectionId)
        AND (
            :filter = 'ALL'
            OR (:filter = 'FAVORITES' AND isFavorite = 1)
            OR (:filter = 'READ_LATER' AND isReadLater = 1)
            OR (:filter = 'ARCHIVED' AND isArchived = 1)
            OR (:filter = 'UNREAD' AND visitCount = 0)
        )
        ORDER BY
        CASE WHEN :sort = 'NEWEST' THEN createdAt END DESC,
        CASE WHEN :sort = 'OLDEST' THEN createdAt END ASC,
        CASE WHEN :sort = 'RECENTLY_OPENED' THEN lastOpenedAt END DESC,
        CASE WHEN :sort = 'MOST_OPENED' THEN visitCount END DESC,
        CASE WHEN :sort = 'PRIORITY' THEN priority END DESC,
        CASE WHEN :sort = 'ALPHABETICAL' THEN title END ASC
        """
    )
    fun pagingSource(
        categoryId: Long?,
        collectionId: Long?,
        filter: String,
        sort: String
    ): PagingSource<Int, LinkEntity>

    @Query(
        """
        SELECT * FROM links
        WHERE isArchived = 0 AND (
            title LIKE '%' || :query || '%' OR
            description LIKE '%' || :query || '%' OR
            notes LIKE '%' || :query || '%' OR
            url LIKE '%' || :query || '%' OR
            domain LIKE '%' || :query || '%' OR
            tagsCsv LIKE '%' || :query || '%'
        )
        ORDER BY createdAt DESC
        """
    )
    fun search(query: String): Flow<List<LinkEntity>>

    @Query("SELECT * FROM links WHERE isFavorite = 1 ORDER BY updatedAt DESC")
    fun observeFavorites(): Flow<List<LinkEntity>>

    @Query("SELECT * FROM links WHERE isReadLater = 1 AND isArchived = 0 ORDER BY createdAt DESC")
    fun observeReadLater(): Flow<List<LinkEntity>>

    @Query("SELECT * FROM links WHERE isArchived = 1 ORDER BY updatedAt DESC")
    fun observeArchived(): Flow<List<LinkEntity>>

    @Query("SELECT COUNT(*) FROM links WHERE isArchived = 0")
    fun observeTotalCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM links WHERE createdAt >= :startOfDay")
    suspend fun countSince(startOfDay: Long): Int

    @Query("SELECT COUNT(*) FROM links WHERE isFavorite = 1")
    suspend fun countFavorites(): Int

    @Query("SELECT COUNT(*) FROM links WHERE isReadLater = 1 AND isArchived = 0")
    suspend fun countReadLater(): Int

    @Query("UPDATE links SET isFavorite = :isFavorite, updatedAt = :now WHERE id = :id")
    suspend fun setFavorite(id: Long, isFavorite: Boolean, now: Long)

    @Query("UPDATE links SET isReadLater = :isReadLater, updatedAt = :now WHERE id = :id")
    suspend fun setReadLater(id: Long, isReadLater: Boolean, now: Long)

    @Query("UPDATE links SET isArchived = :isArchived, updatedAt = :now WHERE id = :id")
    suspend fun setArchived(id: Long, isArchived: Boolean, now: Long)

    @Query("UPDATE links SET visitCount = visitCount + 1, lastOpenedAt = :now WHERE id = :id")
    suspend fun markOpened(id: Long, now: Long)

    @Query("SELECT * FROM links ORDER BY createdAt DESC")
    suspend fun getAllForExport(): List<LinkEntity>

    @Query("DELETE FROM links")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(links: List<LinkEntity>)
}
