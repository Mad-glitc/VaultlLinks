package com.vaultlinks.app.data.repository

import com.vaultlinks.app.data.local.dao.CollectionDao
import com.vaultlinks.app.domain.model.Collection
import com.vaultlinks.app.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollectionRepositoryImpl @Inject constructor(
    private val collectionDao: CollectionDao
) : CollectionRepository {

    override fun observeRoot(): Flow<List<Collection>> =
        collectionDao.observeRootCollections().map { list -> list.map { withCount(it) } }

    override fun observeChildren(parentId: Long): Flow<List<Collection>> =
        collectionDao.observeSubCollections(parentId).map { list -> list.map { withCount(it) } }

    override fun observeAll(): Flow<List<Collection>> =
        collectionDao.observeAll().map { list -> list.map { withCount(it) } }

    override suspend fun getById(id: Long): Collection? = collectionDao.getById(id)?.let { withCount(it) }

    override suspend fun create(collection: Collection): Long =
        collectionDao.insert(collection.copy(createdAt = System.currentTimeMillis()).toEntity())

    override suspend fun update(collection: Collection) = collectionDao.update(collection.toEntity())

    override suspend fun delete(collection: Collection) = collectionDao.delete(collection.toEntity())

    private suspend fun withCount(entity: com.vaultlinks.app.data.local.entity.CollectionEntity): Collection {
        val count = collectionDao.observeLinkCount(entity.id).first()
        return entity.toDomain(count)
    }
}
