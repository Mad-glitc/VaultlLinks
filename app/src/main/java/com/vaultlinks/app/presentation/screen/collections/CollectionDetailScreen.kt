package com.vaultlinks.app.presentation.screen.collections

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.vaultlinks.app.presentation.components.LinkCard
import com.vaultlinks.app.presentation.components.SkeletonCard
import com.vaultlinks.app.presentation.theme.CategoryPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionDetailScreen(
    onBack: () -> Unit,
    onLinkClick: (Long) -> Unit,
    viewModel: CollectionDetailViewModel = hiltViewModel()
) {
    val collection by viewModel.collection.collectAsStateWithLifecycle()
    val isDeleted by viewModel.isDeleted.collectAsStateWithLifecycle()
    val pagingItems = viewModel.pagedLinks.collectAsLazyPagingItems()
    var showDeleteConfirm by remember { mutableStateOf(false) }

    LaunchedEffect(isDeleted) {
        if (isDeleted) onBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(collection?.name ?: "Collection") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "Back") }
                },
                actions = {
                    IconButton(onClick = { showDeleteConfirm = true }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete Collection")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(20.dp)
        ) {
            if (pagingItems.itemCount == 0 && pagingItems.loadState.refresh.let { it is androidx.paging.LoadState.Loading }) {
                items(4) { SkeletonCard(modifier = Modifier.padding(bottom = 14.dp)) }
            }
            items(count = pagingItems.itemCount, key = pagingItems.itemKey { it.id }) { index ->
                val link = pagingItems[index] ?: return@items
                LinkCard(
                    link = link,
                    categoryLabel = null,
                    categoryColor = CategoryPalette[(link.id % CategoryPalette.size).toInt()],
                    onClick = { onLinkClick(link.id) },
                    onFavoriteClick = { viewModel.toggleFavorite(link) },
                    modifier = Modifier.padding(bottom = 14.dp)
                )
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Delete Collection?") },
            text = { Text("This will delete the collection, but your saved links will remain in the vault.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteConfirm = false
                    viewModel.deleteCollection()
                }) {
                    Text("Delete", color = androidx.compose.material3.MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
