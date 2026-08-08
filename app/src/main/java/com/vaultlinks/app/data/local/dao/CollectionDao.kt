package com.vaultlinks.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vaultlinks.app.data.local.entity.CollectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collection: CollectionEntity): Long

    @Update
    suspend fun update(collection: CollectionEntity)

    @Delete
    suspend fun delete(collection: CollectionEntity)

    @Query("SELECT * FROM collections WHERE parentCollectionId IS NULL ORDER BY name ASC")
    fun observeRootCollections(): Flow<List<CollectionEntity>>

    @Query("SELECT * FROM collections WHERE parentCollectionId = :parentId ORDER BY name ASC")
    fun observeSubCollections(parentId: Long): Flow<List<CollectionEntity>>

    @Query("SELECT * FROM collections ORDER BY name ASC")
    fun observeAll(): Flow<List<CollectionEntity>>

    @Query("SELECT * FROM collections WHERE id = :id")
    suspend fun getById(id: Long): CollectionEntity?

    @Query("SELECT COUNT(*) FROM links WHERE collectionId = :collectionId AND isArchived = 0")
    fun observeLinkCount(collectionId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM collections")
    suspend fun count(): Int
}
