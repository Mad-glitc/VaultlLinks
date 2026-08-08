package com.vaultlinks.app.data.repository

import com.vaultlinks.app.data.local.dao.PasswordDao
import com.vaultlinks.app.data.local.entity.PasswordEntity
import com.vaultlinks.app.domain.model.Password
import com.vaultlinks.app.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PasswordRepositoryImpl @Inject constructor(
    private val passwordDao: PasswordDao
) : PasswordRepository {

    override fun observeAll(): Flow<List<Password>> {
        return passwordDao.observeAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getById(id: Long): Password? {
        return passwordDao.getById(id)?.toDomain()
    }

    override suspend fun save(password: Password): Long {
        return passwordDao.insert(password.toEntity())
    }

    override suspend fun delete(password: Password) {
        passwordDao.delete(password.toEntity())
    }

    private fun PasswordEntity.toDomain() = Password(
        id = id,
        title = title,
        username = username,
        passwordEncrypted = passwordEncrypted,
        website = website,
        notes = notes,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    private fun Password.toEntity() = PasswordEntity(
        id = id,
        title = title,
        username = username,
        passwordEncrypted = passwordEncrypted,
        website = website,
        notes = notes,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
