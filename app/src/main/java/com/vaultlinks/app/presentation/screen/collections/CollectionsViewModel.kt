package com.vaultlinks.app.presentation.screen.collections

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaultlinks.app.domain.model.Collection
import com.vaultlinks.app.domain.repository.CollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CollectionsUiState(
    val collections: List<Collection> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class CollectionsViewModel @Inject constructor(
    private val collectionRepository: CollectionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CollectionsUiState())
    val uiState: StateFlow<CollectionsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            collectionRepository.observeRoot().collect { list ->
                _uiState.value = CollectionsUiState(collections = list, isLoading = false)
            }
        }
    }

    fun createCollection(name: String, description: String, colorHex: String) {
        viewModelScope.launch {
            collectionRepository.create(
                Collection(name = name, description = description, parentCollectionId = null, colorHex = colorHex, createdAt = System.currentTimeMillis())
            )
        }
    }

    fun deleteCollection(collection: Collection) {
        viewModelScope.launch { collectionRepository.delete(collection) }
    }
}
