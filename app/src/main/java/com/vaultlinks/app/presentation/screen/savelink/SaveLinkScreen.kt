package com.vaultlinks.app.presentation.screen.savelink

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.vaultlinks.app.domain.model.Priority
import com.vaultlinks.app.presentation.theme.VaultAmber
import com.vaultlinks.app.presentation.theme.VaultRadii

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveLinkScreen(
    prefillUrl: String?,
    onDismiss: () -> Unit,
    onSaved: () -> Unit,
    viewModel: SaveLinkViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val clipboardManager = LocalClipboardManager.current

    LaunchedEffect(prefillUrl) {
        if (!prefillUrl.isNullOrBlank()) viewModel.prefillUrl(prefillUrl)
    }

    LaunchedEffect(state.saveComplete) {
        if (state.saveComplete) onSaved()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Save Link") },
                navigationIcon = {
                    IconButton(onClick = onDismiss) { Icon(Icons.Filled.Close, contentDescription = "Close") }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = state.url,
                    onValueChange = viewModel::onUrlChange,
                    label = { Text("URL") },
                    placeholder = { Text("https://...") },
                    isError = state.urlError != null,
                    supportingText = state.urlError?.let { { Text(it) } },
                    trailingIcon = {
                        IconButton(onClick = {
                            clipboardManager.getText()?.text?.let { viewModel.onUrlChange(it) }
                        }) { Icon(Icons.Filled.ContentPaste, contentDescription = "Paste") }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = VaultRadii.button
                )
            }

            item {
                Button(
                    onClick = { viewModel.fetchMetadata() },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    enabled = state.url.isNotBlank() && !state.isFetchingMetadata,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    if (state.isFetchingMetadata) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Color.White, strokeWidth = 2.dp)
                        Spacer(Modifier.width(8.dp))
                        Text("Fetching preview…")
                    } else {
                        Text("Fetch Preview")
                    }
                }
            }

            if (state.previewImageUrl != null) {
                item {
                    AsyncImage(
                        model = state.previewImageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(180.dp)
                    )
                }
            }

            item {
                OutlinedTextField(
                    value = state.title,
                    onValueChange = viewModel::onTitleChange,
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = VaultRadii.button
                )
            }

            item {
                OutlinedTextField(
                    value = state.description,
                    onValueChange = viewModel::onDescriptionChange,
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2, maxLines = 4,
                    shape = VaultRadii.button
                )
            }

            item {
                OutlinedTextField(
                    value = state.notes,
                    onValueChange = viewModel::onNotesChange,
                    label = { Text("Personal Notes") },
                    placeholder = { Text("Why did you save this?") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2, maxLines = 5,
                    shape = VaultRadii.button
                )
            }

            item {
                OutlinedTextField(
                    value = state.tagsInput,
                    onValueChange = viewModel::onTagsChange,
                    label = { Text("Tags") },
                    placeholder = { Text("android, jetpack-compose, tutorial") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = VaultRadii.button
                )
            }

            item {
                Text("Priority", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Priority.entries.forEach { p ->
                        FilterChip(
                            selected = state.priority == p,
                            onClick = { viewModel.onPriorityChange(p) },
                            label = { Text(p.label) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = VaultAmber.copy(alpha = 0.2f),
                                selectedLabelColor = VaultAmber
                            )
                        )
                    }
                }
            }

            if (state.categories.isNotEmpty()) {
                item {
                    Text("Category", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(state.categories, key = { it.id }) { category ->
                            FilterChip(
                                selected = state.categoryId == category.id,
                                onClick = {
                                    viewModel.onCategoryChange(if (state.categoryId == category.id) null else category.id)
                                },
                                label = { Text(category.name) }
                            )
                        }
                    }
                }
            }

            if (state.collections.isNotEmpty()) {
                item {
                    Text("Collection", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(state.collections, key = { it.id }) { collection ->
                            FilterChip(
                                selected = state.collectionId == collection.id,
                                onClick = {
                                    viewModel.onCollectionChange(if (state.collectionId == collection.id) null else collection.id)
                                },
                                label = { Text(collection.name) }
                            )
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = viewModel::save,
                    enabled = state.url.isNotBlank() && !state.isSaving,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = VaultRadii.button
                ) {
                    if (state.isSaving) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                    } else {
                        Text("Save to Vault", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}
