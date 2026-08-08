package com.vaultlinks.app.presentation.screen.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vaultlinks.app.presentation.components.EmptyState
import com.vaultlinks.app.presentation.components.LinkCard
import com.vaultlinks.app.presentation.components.LinkCardCompact
import com.vaultlinks.app.presentation.components.VaultSearchBar
import com.vaultlinks.app.presentation.theme.CategoryPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onLinkClick: (Long) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites") },
                actions = {
                    IconButton(onClick = {
                        viewModel.setViewMode(if (state.viewMode == FavoritesViewMode.GRID) FavoritesViewMode.LIST else FavoritesViewMode.GRID)
                    }) {
                        Icon(
                            if (state.viewMode == FavoritesViewMode.GRID) Icons.Filled.ViewList else Icons.Filled.GridView,
                            contentDescription = "Toggle view"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            VaultSearchBar(
                query = state.query,
                onQueryChange = viewModel::setQuery,
                placeholder = "Search favorites…",
                modifier = Modifier.padding(20.dp)
            )

            if (state.links.isEmpty()) {
                EmptyState(
                    icon = Icons.Outlined.FavoriteBorder,
                    title = "No favorites yet",
                    subtitle = "Tap the heart on any saved link to pin it here for quick access."
                )
            } else if (state.viewMode == FavoritesViewMode.GRID) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 24.dp),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(14.dp),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(14.dp)
                ) {
                    items(state.links, key = { it.id }) { link ->
                        LinkCard(
                            link = link,
                            categoryLabel = null,
                            categoryColor = CategoryPalette[(link.id % CategoryPalette.size).toInt()],
                            onClick = { onLinkClick(link.id) },
                            onFavoriteClick = { viewModel.toggleFavorite(link) }
                        )
                    }
                }
            } else {
                LazyColumn(contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp)) {
                    items(state.links, key = { it.id }) { link ->
                        LinkCardCompact(
                            link = link,
                            categoryColor = CategoryPalette[(link.id % CategoryPalette.size).toInt()],
                            onClick = { onLinkClick(link.id) },
                            onFavoriteClick = { viewModel.toggleFavorite(link) },
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                }
            }
        }
    }
}
