package com.vaultlinks.app.presentation.screen.linkdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaultlinks.app.domain.model.Category
import com.vaultlinks.app.domain.model.Link
import com.vaultlinks.app.domain.repository.CategoryRepository
import com.vaultlinks.app.domain.repository.LinkRepository
import com.vaultlinks.app.domain.usecase.DeleteLinkUseCase
import com.vaultlinks.app.domain.usecase.MarkOpenedUseCase
import com.vaultlinks.app.domain.usecase.ToggleArchivedUseCase
import com.vaultlinks.app.domain.usecase.ToggleFavoriteUseCase
import com.vaultlinks.app.domain.usecase.ToggleReadLaterUseCase
import com.vaultlinks.app.domain.usecase.UpdateLinkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.vaultlinks.app.domain.model.LinkNote
import com.vaultlinks.app.domain.repository.LinkNoteRepository

data class LinkDetailUiState(
    val link: Link? = null,
    val categories: List<Category> = emptyList(),
    val notes: List<LinkNote> = emptyList(),
    val isDeleted: Boolean = false
)

@HiltViewModel
class LinkDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val linkRepository: LinkRepository,
    private val categoryRepository: CategoryRepository,
    private val markOpenedUseCase: MarkOpenedUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val toggleReadLaterUseCase: ToggleReadLaterUseCase,
    private val toggleArchivedUseCase: ToggleArchivedUseCase,
    private val deleteLinkUseCase: DeleteLinkUseCase,
    private val updateLinkUseCase: UpdateLinkUseCase,
    private val linkNoteRepository: LinkNoteRepository
) : ViewModel() {

    private val linkId: Long = checkNotNull(savedStateHandle["linkId"])

    private val _uiState = MutableStateFlow(LinkDetailUiState())
    val uiState: StateFlow<LinkDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                linkRepository.observeLink(linkId),
                categoryRepository.observeAll(),
                linkNoteRepository.observeNotesForLink(linkId)
            ) { link: Link?, categories: List<Category>, notes: List<LinkNote> ->
                LinkDetailUiState(link, categories, notes)
            }
                .collect { _uiState.value = it }
        }
    }

    fun onOpened() {
        viewModelScope.launch { markOpenedUseCase(linkId) }
    }

    fun toggleFavorite() {
        val link = _uiState.value.link ?: return
        viewModelScope.launch { toggleFavoriteUseCase(linkId, !link.isFavorite) }
    }

    fun toggleReadLater() {
        val link = _uiState.value.link ?: return
        viewModelScope.launch { toggleReadLaterUseCase(linkId, !link.isReadLater) }
    }

    fun toggleArchived() {
        val link = _uiState.value.link ?: return
        viewModelScope.launch { toggleArchivedUseCase(linkId, !link.isArchived) }
    }

    fun updateNotes(notes: String) {
        val link = _uiState.value.link ?: return
        viewModelScope.launch { updateLinkUseCase(link.copy(notes = notes)) }
    }

    fun addLinkNote(text: String) {
        if (text.isBlank()) return
        viewModelScope.launch { linkNoteRepository.addNote(linkId, text) }
    }

    fun deleteLinkNote(id: Long) {
        viewModelScope.launch { linkNoteRepository.deleteNote(id) }
    }

    fun updateCategory(categoryId: Long?) {
        val link = _uiState.value.link ?: return
        viewModelScope.launch { updateLinkUseCase(link.copy(categoryId = categoryId)) }
    }

    fun delete() {
        val link = _uiState.value.link ?: return
        viewModelScope.launch {
            deleteLinkUseCase(link)
            _uiState.value = _uiState.value.copy(isDeleted = true)
        }
    }
}
