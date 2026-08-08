package com.vaultlinks.app.di

import com.vaultlinks.app.data.repository.CategoryRepositoryImpl
import com.vaultlinks.app.data.repository.CollectionRepositoryImpl
import com.vaultlinks.app.data.repository.LinkNoteRepositoryImpl
import com.vaultlinks.app.data.repository.LinkRepositoryImpl
import com.vaultlinks.app.data.repository.PasswordRepositoryImpl
import com.vaultlinks.app.domain.repository.CategoryRepository
import com.vaultlinks.app.domain.repository.CollectionRepository
import com.vaultlinks.app.domain.repository.LinkNoteRepository
import com.vaultlinks.app.domain.repository.LinkRepository
import com.vaultlinks.app.domain.repository.PasswordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLinkRepository(impl: LinkRepositoryImpl): LinkRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindCollectionRepository(impl: CollectionRepositoryImpl): CollectionRepository

    @Binds
    @Singleton
    abstract fun bindLinkNoteRepository(impl: LinkNoteRepositoryImpl): LinkNoteRepository

    @Binds
    @Singleton
    abstract fun bindPasswordRepository(impl: PasswordRepositoryImpl): PasswordRepository
}
