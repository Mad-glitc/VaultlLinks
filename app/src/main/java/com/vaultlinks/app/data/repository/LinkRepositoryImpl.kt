package com.vaultlinks.app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.vaultlinks.app.data.local.dao.CategoryDao
import com.vaultlinks.app.data.local.dao.CollectionDao
import com.vaultlinks.app.data.local.dao.LinkDao
import com.vaultlinks.app.domain.model.Link
import com.vaultlinks.app.domain.model.LinkFilter
import com.vaultlinks.app.domain.model.SortOrder
import com.vaultlinks.app.domain.model.VaultStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkRepositoryImpl @Inject constructor(
    private val linkDao: LinkDao,
    private val categoryDao: CategoryDao,
    private val collectionDao: CollectionDao
) : com.vaultlinks.app.domain.repository.LinkRepository {

    override suspend fun saveLink(link: Link): Long {
        val now = System.currentTimeMillis()
        return linkDao.insert(link.copy(createdAt = link.createdAt.takeIf { it > 0 } ?: now, updatedAt = now).toEntity())
    }

    override suspend fun updateLink(link: Link) {
        linkDao.update(link.copy(updatedAt = System.currentTimeMillis()).toEntity())
    }

    override suspend fun deleteLink(link: Link) = linkDao.delete(link.toEntity())

    override suspend fun getLink(id: Long): Link? = linkDao.getById(id)?.toDomain()

    override fun observeLink(id: Long): Flow<Link?> = linkDao.observeById(id).map { it?.toDomain() }

    override fun observeRecent(limit: Int): Flow<List<Link>> =
        linkDao.observeRecent(limit).map { list -> list.map { it.toDomain() } }

    override fun observePinned(limit: Int): Flow<List<Link>> =
        linkDao.observePinned(limit).map { list -> list.map { it.toDomain() } }

    override fun observeRecentlyViewed(limit: Int): Flow<List<Link>> =
        linkDao.observeRecentlyViewed(limit).map { list -> list.map { it.toDomain() } }

    override fun pagedLinks(
        categoryId: Long?,
        collectionId: Long?,
        filter: LinkFilter,
        sort: SortOrder
    ): Flow<PagingData<Link>> = Pager(
        config = PagingConfig(pageSize = 30, prefetchDistance = 10, enablePlaceholders = false),
        pagingSourceFactory = {
            linkDao.pagingSource(categoryId, collectionId, filter.name, sort.name)
        }
    ).flow.map { pagingData -> pagingData.map { it.toDomain() } }

    override fun search(query: String): Flow<List<Link>> =
        linkDao.search(query).map { list -> list.map { it.toDomain() } }

    override fun observeFavorites(): Flow<List<Link>> =
        linkDao.observeFavorites().map { list -> list.map { it.toDomain() } }

    override fun observeReadLater(): Flow<List<Link>> =
        linkDao.observeReadLater().map { list -> list.map { it.toDomain() } }

    override fun observeArchived(): Flow<List<Link>> =
        linkDao.observeArchived().map { list -> list.map { it.toDomain() } }

    override fun observeTotalCount(): Flow<Int> = linkDao.observeTotalCount()

    override suspend fun toggleFavorite(id: Long, isFavorite: Boolean) =
        linkDao.setFavorite(id, isFavorite, System.currentTimeMillis())

    override suspend fun toggleReadLater(id: Long, isReadLater: Boolean) =
        linkDao.setReadLater(id, isReadLater, System.currentTimeMillis())

    override suspend fun toggleArchived(id: Long, isArchived: Boolean) =
        linkDao.setArchived(id, isArchived, System.currentTimeMillis())

    override suspend fun markOpened(id: Long) = linkDao.markOpened(id, System.currentTimeMillis())

    override suspend fun getStats(): VaultStats {
        val total = linkDao.observeTotalCount().first()
        val cal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0); set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = cal.timeInMillis
        val startOfWeek = startOfDay - 6L * 24 * 60 * 60 * 1000
        val savedToday = linkDao.countSince(startOfDay)
        val savedThisWeek = linkDao.countSince(startOfWeek)
        val favorites = linkDao.countFavorites()
        val readLater = linkDao.countReadLater()
        val categoriesCount = categoryDao.count()
        val collectionsCount = collectionDao.count()
        val topCategories = categoryDao.observeWithCounts().first()
            .sortedByDescending { it.linkCount }
            .take(5)
            .map { it.let { c -> com.vaultlinks.app.domain.model.Category(c.id, c.name, c.colorHex, c.iconName, c.linkCount) to c.linkCount } }

        // Rough estimate: average text-heavy row ~1.5KB. Good enough for a "storage used" UI hint,
        // not a byte-accurate disk measurement (would require a full DB file stat).
        val estimatedStorageBytes = total * 1536L

        return VaultStats(
            totalLinks = total,
            savedToday = savedToday,
            savedThisWeek = savedThisWeek,
            favoritesCount = favorites,
            readLaterCount = readLater,
            categoriesCount = categoriesCount,
            collectionsCount = collectionsCount,
            estimatedStorageBytes = estimatedStorageBytes,
            topCategories = topCategories
        )
    }

    override suspend fun exportAll(): List<Link> = linkDao.getAllForExport().map { it.toDomain() }

    override suspend fun importAll(links: List<Link>) = linkDao.insertAll(links.map { it.toEntity() })

    override suspend fun clearAll() = linkDao.deleteAll()
}
