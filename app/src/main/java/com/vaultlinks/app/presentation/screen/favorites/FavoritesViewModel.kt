package com.vaultlinks.app.presentation.screen.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaultlinks.app.domain.model.Link
import com.vaultlinks.app.domain.repository.LinkRepository
import com.vaultlinks.app.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class FavoritesViewMode { GRID, LIST }

data class FavoritesUiState(
    val links: List<Link> = emptyList(),
    val viewMode: FavoritesViewMode = FavoritesViewMode.GRID,
    val query: String = ""
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val linkRepository: LinkRepository,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _viewMode = MutableStateFlow(FavoritesViewMode.GRID)
    private val _query = MutableStateFlow("")

    val uiState: StateFlow<FavoritesUiState> = combine(
        linkRepository.observeFavorites(), _viewMode, _query
    ) { links, mode, query ->
        val filtered = if (query.isBlank()) links else links.filter {
            it.title.contains(query, ignoreCase = true) || it.domain.contains(query, ignoreCase = true)
        }
        FavoritesUiState(filtered, mode, query)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), FavoritesUiState())

    fun setViewMode(mode: FavoritesViewMode) { _viewMode.value = mode }
    fun setQuery(query: String) { _query.value = query }

    fun toggleFavorite(link: Link) {
        viewModelScope.launch { toggleFavoriteUseCase(link.id, !link.isFavorite) }
    }
}
