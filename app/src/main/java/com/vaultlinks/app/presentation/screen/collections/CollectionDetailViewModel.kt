package com.vaultlinks.app.presentation.screen.collections

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vaultlinks.app.domain.model.Collection
import com.vaultlinks.app.domain.model.Link
import com.vaultlinks.app.domain.model.LinkFilter
import com.vaultlinks.app.domain.model.SortOrder
import com.vaultlinks.app.domain.repository.CollectionRepository
import com.vaultlinks.app.domain.repository.LinkRepository
import com.vaultlinks.app.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val linkRepository: LinkRepository,
    private val collectionRepository: CollectionRepository,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val collectionId: Long = checkNotNull(savedStateHandle["collectionId"])

    private val _sortOrder = MutableStateFlow(SortOrder.NEWEST)
    val sortOrder: StateFlow<SortOrder> = _sortOrder.asStateFlow()

    private val _collection = MutableStateFlow<Collection?>(null)
    val collection: StateFlow<Collection?> = _collection.asStateFlow()

    val pagedLinks: Flow<PagingData<Link>> = _sortOrder.flatMapLatest { sort ->
        linkRepository.pagedLinks(categoryId = null, collectionId = collectionId, filter = LinkFilter.ALL, sort = sort)
    }.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            _collection.value = collectionRepository.getById(collectionId)
        }
    }

    fun setSortOrder(sort: SortOrder) { _sortOrder.value = sort }

    fun toggleFavorite(link: Link) {
        viewModelScope.launch { toggleFavoriteUseCase(link.id, !link.isFavorite) }
    }

    private val _isDeleted = MutableStateFlow(false)
    val isDeleted: StateFlow<Boolean> = _isDeleted.asStateFlow()

    fun deleteCollection() {
        viewModelScope.launch {
            val coll = _collection.value ?: return@launch
            collectionRepository.delete(coll)
            _isDeleted.value = true
        }
    }
}
