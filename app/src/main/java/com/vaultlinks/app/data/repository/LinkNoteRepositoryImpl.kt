package com.vaultlinks.app.data.repository

import com.vaultlinks.app.data.local.dao.LinkNoteDao
import com.vaultlinks.app.data.local.entity.LinkNoteEntity
import com.vaultlinks.app.domain.model.LinkNote
import com.vaultlinks.app.domain.repository.LinkNoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkNoteRepositoryImpl @Inject constructor(
    private val linkNoteDao: LinkNoteDao
) : LinkNoteRepository {

    override fun observeNotesForLink(linkId: Long): Flow<List<LinkNote>> {
        return linkNoteDao.observeNotesForLink(linkId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addNote(linkId: Long, text: String): Long {
        val entity = LinkNoteEntity(
            linkId = linkId,
            text = text,
            createdAt = System.currentTimeMillis()
        )
        return linkNoteDao.insert(entity)
    }

    override suspend fun deleteNote(id: Long) {
        linkNoteDao.deleteById(id)
    }

    private fun LinkNoteEntity.toDomain() = LinkNote(
        id = id,
        linkId = linkId,
        text = text,
        createdAt = createdAt
    )
}
