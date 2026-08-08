package com.vaultlinks.app.presentation.screen.savelink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaultlinks.app.data.metadata.MetadataFetcher
import com.vaultlinks.app.domain.model.Category
import com.vaultlinks.app.domain.model.Collection
import com.vaultlinks.app.domain.model.LinkMetadata
import com.vaultlinks.app.domain.model.LinkPlatform
import com.vaultlinks.app.domain.model.Priority
import com.vaultlinks.app.domain.repository.CategoryRepository
import com.vaultlinks.app.domain.repository.CollectionRepository
import com.vaultlinks.app.domain.usecase.SaveLinkUseCase
import com.vaultlinks.app.worker.WorkScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SaveLinkUiState(
    val url: String = "",
    val title: String = "",
    val description: String = "",
    val notes: String = "",
    val tagsInput: String = "",
    val priority: Priority = Priority.NORMAL,
    val categoryId: Long? = null,
    val collectionId: Long? = null,
    val isFetchingMetadata: Boolean = false,
    val metadataFetched: Boolean = false,
    val previewImageUrl: String? = null,
    val faviconUrl: String? = null,
    val platform: LinkPlatform = LinkPlatform.GENERIC_WEB,
    val categories: List<Category> = emptyList(),
    val collections: List<Collection> = emptyList(),
    val isSaving: Boolean = false,
    val saveComplete: Boolean = false,
    val urlError: String? = null
)

@HiltViewModel
class SaveLinkViewModel @Inject constructor(
    private val saveLinkUseCase: SaveLinkUseCase,
    private val metadataFetcher: MetadataFetcher,
    private val categoryRepository: CategoryRepository,
    private val collectionRepository: CollectionRepository,
    private val workScheduler: WorkScheduler
) : ViewModel() {

    private val _uiState = MutableStateFlow(SaveLinkUiState())
    val uiState: StateFlow<SaveLinkUiState> = _uiState.asStateFlow()

    private var savedLinkId: Long? = null

    init {
        viewModelScope.launch {
            categoryRepository.observeAll().collect { cats ->
                _uiState.value = _uiState.value.copy(categories = cats)
            }
        }
        viewModelScope.launch {
            collectionRepository.observeAll().collect { cols ->
                _uiState.value = _uiState.value.copy(collections = cols)
            }
        }
    }

    fun prefillUrl(url: String) {
        _uiState.value = _uiState.value.copy(url = url, platform = LinkPlatform.fromUrl(url))
        fetchMetadata(url)
    }

    fun onUrlChange(url: String) {
        _uiState.value = _uiState.value.copy(url = url, urlError = null, platform = LinkPlatform.fromUrl(url))
    }

    fun fetchMetadata(urlOverride: String? = null) {
        val url = (urlOverride ?: _uiState.value.url).trim()
        if (!isValidUrl(url)) {
            _uiState.value = _uiState.value.copy(urlError = "Enter a valid URL starting with http:// or https://")
            return
        }
        _uiState.value = _uiState.value.copy(isFetchingMetadata = true, urlError = null)
        viewModelScope.launch {
            val meta = runCatching { metadataFetcher.fetch(url) }.getOrNull()
            applyMetadata(meta, url)
        }
    }

    private fun applyMetadata(meta: LinkMetadata?, url: String) {
        val domain = runCatching { java.net.URI(url).host ?: url }.getOrDefault(url)
        _uiState.value = _uiState.value.copy(
            isFetchingMetadata = false,
            metadataFetched = true,
            title = meta?.title ?: _uiState.value.title.ifBlank { domain },
            description = meta?.description ?: _uiState.value.description,
            previewImageUrl = meta?.previewImageUrl,
            faviconUrl = meta?.faviconUrl
        )
    }

    fun onTitleChange(title: String) { _uiState.value = _uiState.value.copy(title = title) }
    fun onDescriptionChange(desc: String) { _uiState.value = _uiState.value.copy(description = desc) }
    fun onNotesChange(notes: String) { _uiState.value = _uiState.value.copy(notes = notes) }
    fun onTagsChange(tags: String) { _uiState.value = _uiState.value.copy(tagsInput = tags) }
    fun onPriorityChange(priority: Priority) { _uiState.value = _uiState.value.copy(priority = priority) }
    fun onCategoryChange(id: Long?) { _uiState.value = _uiState.value.copy(categoryId = id) }
    fun onCollectionChange(id: Long?) { _uiState.value = _uiState.value.copy(collectionId = id) }

    fun save() {
        val state = _uiState.value
        if (!isValidUrl(state.url.trim())) {
            _uiState.value = state.copy(urlError = "Enter a valid URL starting with http:// or https://")
            return
        }
        _uiState.value = state.copy(isSaving = true)
        viewModelScope.launch {
            val tags = state.tagsInput.split(",", " ").map { it.trim().removePrefix("#") }.filter { it.isNotEmpty() }
            val id = saveLinkUseCase(
                url = state.url.trim(),
                title = state.title.ifBlank { null },
                description = state.description.ifBlank { null },
                notes = state.notes,
                categoryId = state.categoryId,
                collectionId = state.collectionId,
                tags = tags,
                priority = state.priority,
                fetchMetadataIfMissing = !state.metadataFetched
            )
            savedLinkId = id
            // Guarantee enrichment completes even if the screen closes right after save.
            workScheduler.enqueueMetadataFetch(id)
            _uiState.value = _uiState.value.copy(isSaving = false, saveComplete = true)
        }
    }

    private fun isValidUrl(url: String): Boolean =
        url.startsWith("http://") || url.startsWith("https://")
}
