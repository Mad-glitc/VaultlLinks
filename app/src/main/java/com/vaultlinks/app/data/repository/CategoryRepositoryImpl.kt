package com.vaultlinks.app.data.repository

import com.vaultlinks.app.data.local.dao.CategoryDao
import com.vaultlinks.app.data.local.entity.CategoryEntity
import com.vaultlinks.app.domain.model.Category
import com.vaultlinks.app.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun observeAll(): Flow<List<Category>> =
        categoryDao.observeWithCounts().map { list -> list.map { Category(it.id, it.name, it.colorHex, it.iconName, it.linkCount) } }

    override suspend fun getById(id: Long): Category? = categoryDao.getById(id)?.toDomain()

    override suspend fun create(category: Category): Long = categoryDao.insert(category.toEntity())

    override suspend fun update(category: Category) = categoryDao.update(category.toEntity())

    override suspend fun delete(category: Category) = categoryDao.delete(category.toEntity())

    override suspend fun ensureDefaults() {
        if (categoryDao.count() > 0) return
        val defaults = listOf(
            "Android" to "#3DDC84", "AI" to "#8E44AD", "Coding" to "#2ECC71",
            "Business" to "#F39C12", "Finance" to "#16A085", "Fitness" to "#E74C3C",
            "College" to "#3498DB", "Travel" to "#E67E22", "Shopping" to "#EC407A",
            "Books" to "#795548", "Research" to "#1ABC9C", "Design" to "#9C27B0",
            "Career" to "#607D8B", "Ideas" to "#FFC107", "Projects" to "#6C5CE7",
            "Recipes" to "#FF7043", "Movies" to "#D32F2F", "Music" to "#7B1FA2",
            "Learning" to "#0288D1", "Personal" to "#546E7A"
        ).map { (name, color) -> CategoryEntity(name = name, colorHex = color, iconName = "folder") }
        categoryDao.insertDefaults(defaults)
    }
}
