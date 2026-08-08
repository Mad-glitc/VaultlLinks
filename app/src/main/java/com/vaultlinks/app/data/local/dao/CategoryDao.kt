package com.vaultlinks.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vaultlinks.app.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryEntity): Long

    @Update
    suspend fun update(category: CategoryEntity)

    @Delete
    suspend fun delete(category: CategoryEntity)

    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun observeAll(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getById(id: Long): CategoryEntity?

    @Query(
        """
        SELECT categories.*, COUNT(links.id) as linkCount
        FROM categories
        LEFT JOIN links ON links.categoryId = categories.id AND links.isArchived = 0
        GROUP BY categories.id
        ORDER BY linkCount DESC
        """
    )
    fun observeWithCounts(): Flow<List<CategoryWithCount>>

    @Query("SELECT COUNT(*) FROM categories")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDefaults(defaults: List<CategoryEntity>)
}

data class CategoryWithCount(
    val id: Long,
    val name: String,
    val colorHex: String,
    val iconName: String,
    val linkCount: Int
)
