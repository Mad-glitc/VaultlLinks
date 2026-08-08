package com.vaultlinks.app.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaultlinks.app.domain.model.Category
import com.vaultlinks.app.domain.model.Collection
import com.vaultlinks.app.domain.model.Link
import com.vaultlinks.app.domain.model.VaultStats
import com.vaultlinks.app.domain.repository.CategoryRepository
import com.vaultlinks.app.domain.repository.CollectionRepository
import com.vaultlinks.app.domain.repository.LinkRepository
import com.vaultlinks.app.domain.usecase.GetStatsUseCase
import com.vaultlinks.app.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = true,
    val recentSaves: List<Link> = emptyList(),
    val pinnedLinks: List<Link> = emptyList(),
    val recentlyViewed: List<Link> = emptyList(),
    val categories: List<Category> = emptyList(),
    val collections: List<Collection> = emptyList(),
    val stats: VaultStats? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val linkRepository: LinkRepository,
    private val categoryRepository: CategoryRepository,
    private val collectionRepository: CollectionRepository,
    private val getStatsUseCase: GetStatsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeDashboard()
        refreshStats()
    }

    private fun observeDashboard() {
        viewModelScope.launch {
            combine(
                linkRepository.observeRecent(10),
                linkRepository.observePinned(8),
                linkRepository.observeRecentlyViewed(10),
                categoryRepository.observeAll(),
                collectionRepository.observeRoot()
            ) { recent, pinned, viewed, categories, collections ->
                HomeUiState(
                    isLoading = false,
                    recentSaves = recent,
                    pinnedLinks = pinned,
                    recentlyViewed = viewed,
                    categories = categories,
                    collections = collections,
                    stats = _uiState.value.stats
                )
            }.collect { newState -> _uiState.value = newState }
        }
    }

    fun refreshStats() {
        viewModelScope.launch {
            val stats = getStatsUseCase()
            _uiState.value = _uiState.value.copy(stats = stats)
        }
    }

    fun toggleFavorite(link: Link) {
        viewModelScope.launch { toggleFavoriteUseCase(link.id, !link.isFavorite) }
    }
}
