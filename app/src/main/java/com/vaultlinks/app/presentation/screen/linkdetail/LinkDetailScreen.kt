package com.vaultlinks.app.presentation.screen.linkdetail

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Unarchive
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.vaultlinks.app.presentation.theme.CategoryPalette
import com.vaultlinks.app.presentation.theme.VaultAmber
import com.vaultlinks.app.presentation.theme.VaultRadii
import com.vaultlinks.app.presentation.theme.VaultViolet
import com.vaultlinks.app.util.ChromeCustomTabsHelper
import com.vaultlinks.app.util.DateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LinkDetailScreen(
    onBack: () -> Unit,
    viewModel: LinkDetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showDeleteConfirm by remember { mutableStateOf(false) }
    var notesDraft by remember(state.link?.id) { mutableStateOf(state.link?.notes.orEmpty()) }
    var notepadDraft by remember { mutableStateOf("") }

    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) onBack()
    }

    val link = state.link ?: run {
        Scaffold(topBar = { TopAppBar(title = { Text("") }, navigationIcon = {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, null) }
        }) }) { padding -> Column(Modifier.fillMaxSize().padding(padding)) {} }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(link.domain, maxLines = 1) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Back") } },
                actions = {
                    IconButton(onClick = {
                        val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(android.content.Intent.EXTRA_TEXT, link.url)
                        }
                        context.startActivity(android.content.Intent.createChooser(shareIntent, "Share link"))
                    }) { Icon(Icons.Filled.Share, "Share") }
                    IconButton(onClick = { showDeleteConfirm = true }) { Icon(Icons.Filled.Delete, "Delete") }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            if (link.previewImageUrl != null) {
                item {
                    AsyncImage(
                        model = link.previewImageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(220.dp)
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = link.faviconUrl, contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(link.domain, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.weight(1f))
                        Text(DateUtils.relativeTime(link.createdAt), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(link.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    if (link.description.isNotBlank()) {
                        Spacer(Modifier.height(8.dp))
                        Text(link.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    if (link.tags.isNotEmpty()) {
                        Spacer(Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            link.tags.take(6).forEach { tag ->
                                AssistChip(onClick = {}, label = { Text("#$tag") })
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    androidx.compose.material3.Button(
                        onClick = {
                            viewModel.onOpened()
                            ChromeCustomTabsHelper.openUrl(context, link.url, android.graphics.Color.parseColor("#6C5CE7"))
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = VaultRadii.button
                    ) {
                        Icon(Icons.Filled.OpenInBrowser, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Open Link")
                    }

                    Spacer(Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                        ActionToggle(
                            label = "Favorite",
                            icon = if (link.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            active = link.isFavorite,
                            color = VaultAmber,
                            onClick = viewModel::toggleFavorite,
                            modifier = Modifier.weight(1f)
                        )
                        ActionToggle(
                            label = "Read Later",
                            icon = Icons.Outlined.BookmarkBorder,
                            active = link.isReadLater,
                            color = VaultViolet,
                            onClick = viewModel::toggleReadLater,
                            modifier = Modifier.weight(1f)
                        )
                        ActionToggle(
                            label = if (link.isArchived) "Unarchive" else "Archive",
                            icon = if (link.isArchived) Icons.Outlined.Unarchive else Icons.Filled.Archive,
                            active = link.isArchived,
                            color = Color(0xFF636E72),
                            onClick = viewModel::toggleArchived,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(Modifier.height(24.dp))
                    Text("Personal Notes", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = notesDraft,
                        onValueChange = { notesDraft = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        placeholder = { Text("Why did you save this? Add your thoughts…") },
                        shape = VaultRadii.button
                    )
                    Spacer(Modifier.height(8.dp))
                    TextButton(onClick = { viewModel.updateNotes(notesDraft) }, modifier = Modifier.align(Alignment.End)) {
                        Text("Save Notes")
                    }

                    if (state.categories.isNotEmpty()) {
                        Spacer(Modifier.height(16.dp))
                        Text("Category", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            state.categories.take(6).forEach { category ->
                                FilterChip(
                                    selected = link.categoryId == category.id,
                                    onClick = { viewModel.updateCategory(if (link.categoryId == category.id) null else category.id) },
                                    label = { Text(category.name) }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(32.dp))
                    Text("Notepad", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = notepadDraft,
                        onValueChange = { notepadDraft = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Add a quick note…") },
                        shape = VaultRadii.button,
                        trailingIcon = {
                            IconButton(onClick = {
                                viewModel.addLinkNote(notepadDraft)
                                notepadDraft = ""
                            }) {
                                Icon(Icons.Filled.Add, "Add note")
                            }
                        }
                    )

                    if (state.notes.isNotEmpty()) {
                        Spacer(Modifier.height(16.dp))
                        state.notes.forEach { note ->
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                shape = VaultRadii.card,
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            DateUtils.relativeTime(note.createdAt),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Spacer(Modifier.weight(1f))
                                        IconButton(onClick = { viewModel.deleteLinkNote(note.id) }, modifier = Modifier.size(24.dp)) {
                                            Icon(Icons.Filled.Close, null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.error)
                                        }
                                    }
                                    Spacer(Modifier.height(4.dp))
                                    Text(note.text, style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }

                    if (link.extras.isNotEmpty()) {
                        Spacer(Modifier.height(20.dp))
                        Card(shape = VaultRadii.card, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                link.extras.forEach { (key, value) ->
                                    Text("${key.replaceFirstChar { it.uppercase() }}: $value", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Delete this link?") },
            text = { Text("This can't be undone.") },
            confirmButton = {
                TextButton(onClick = { showDeleteConfirm = false; viewModel.delete() }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton(onClick = { showDeleteConfirm = false }) { Text("Cancel") } }
        )
    }
}

@Composable
private fun ActionToggle(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    active: Boolean,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = VaultRadii.button,
        color = if (active) color.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .then(Modifier.clickableToggle(onClick)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, null, tint = if (active) color else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
            Spacer(Modifier.height(4.dp))
            Text(label, style = MaterialTheme.typography.labelSmall, color = if (active) color else MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun Modifier.clickableToggle(onClick: () -> Unit): Modifier =
    this.then(
        Modifier.clickable(
            interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
    )
