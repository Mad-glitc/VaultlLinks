package com.vaultlinks.app.domain.repository

import androidx.paging.PagingData
import com.vaultlinks.app.domain.model.Category
import com.vaultlinks.app.domain.model.Collection
import com.vaultlinks.app.domain.model.Link
import com.vaultlinks.app.domain.model.LinkFilter
import com.vaultlinks.app.domain.model.LinkNote
import com.vaultlinks.app.domain.model.Password
import com.vaultlinks.app.domain.model.SortOrder
import com.vaultlinks.app.domain.model.VaultStats
import kotlinx.coroutines.flow.Flow

interface LinkRepository {
    suspend fun saveLink(link: Link): Long
    suspend fun updateLink(link: Link)
    suspend fun deleteLink(link: Link)
    suspend fun getLink(id: Long): Link?
    fun observeLink(id: Long): Flow<Link?>

    fun observeRecent(limit: Int = 10): Flow<List<Link>>
    fun observePinned(limit: Int = 8): Flow<List<Link>>
    fun observeRecentlyViewed(limit: Int = 10): Flow<List<Link>>

    fun pagedLinks(
        categoryId: Long?,
        collectionId: Long?,
        filter: LinkFilter,
        sort: SortOrder
    ): Flow<PagingData<Link>>

    fun search(query: String): Flow<List<Link>>
    fun observeFavorites(): Flow<List<Link>>
    fun observeReadLater(): Flow<List<Link>>
    fun observeArchived(): Flow<List<Link>>
    fun observeTotalCount(): Flow<Int>

    suspend fun toggleFavorite(id: Long, isFavorite: Boolean)
    suspend fun toggleReadLater(id: Long, isReadLater: Boolean)
    suspend fun toggleArchived(id: Long, isArchived: Boolean)
    suspend fun markOpened(id: Long)

    suspend fun getStats(): VaultStats
    suspend fun exportAll(): List<Link>
    suspend fun importAll(links: List<Link>)
    suspend fun clearAll()
}

interface CategoryRepository {
    fun observeAll(): Flow<List<Category>>
    suspend fun getById(id: Long): Category?
    suspend fun create(category: Category): Long
    suspend fun update(category: Category)
    suspend fun delete(category: Category)
    suspend fun ensureDefaults()
}

interface CollectionRepository {
    fun observeRoot(): Flow<List<Collection>>
    fun observeChildren(parentId: Long): Flow<List<Collection>>
    fun observeAll(): Flow<List<Collection>>
    suspend fun getById(id: Long): Collection?
    suspend fun create(collection: Collection): Long
    suspend fun update(collection: Collection)
    suspend fun delete(collection: Collection)
}

interface LinkNoteRepository {
    fun observeNotesForLink(linkId: Long): Flow<List<LinkNote>>
    suspend fun addNote(linkId: Long, text: String): Long
    suspend fun deleteNote(id: Long)
}

interface PasswordRepository {
    fun observeAll(): Flow<List<Password>>
    suspend fun getById(id: Long): Password?
    suspend fun save(password: Password): Long
    suspend fun delete(password: Password)
}
