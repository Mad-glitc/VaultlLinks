package com.vaultlinks.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vaultlinks.app.data.local.entity.LinkNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkNoteDao {
    @Query("SELECT * FROM link_notes WHERE linkId = :linkId ORDER BY createdAt DESC")
    fun observeNotesForLink(linkId: Long): Flow<List<LinkNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: LinkNoteEntity): Long

    @Delete
    suspend fun delete(note: LinkNoteEntity)

    @Query("DELETE FROM link_notes WHERE id = :id")
    suspend fun deleteById(id: Long)
}
