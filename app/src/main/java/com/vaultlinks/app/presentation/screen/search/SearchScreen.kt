package com.vaultlinks.app.presentation.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.vaultlinks.app.presentation.components.LinkCardCompact
import com.vaultlinks.app.presentation.components.VaultSearchBar
import com.vaultlinks.app.presentation.theme.CategoryPalette

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onLinkClick: (Long) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = { TopAppBar(title = { Text("Search") }) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            VaultSearchBar(
                query = state.query,
                onQueryChange = viewModel::onQueryChange,
                modifier = Modifier.padding(20.dp)
            )

            when {
                state.query.isBlank() -> EmptyState(
                    icon = Icons.Outlined.SearchOff,
                    title = "Search your vault",
                    subtitle = "Find anything by title, description, notes, tags, or domain."
                )
                state.results.isEmpty() && !state.isSearching -> EmptyState(
                    icon = Icons.Outlined.SearchOff,
                    title = "No results",
                    subtitle = "Nothing matches \"${state.query}\". Try a different keyword."
                )
                else -> LazyColumn(contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp)) {
                    items(state.results, key = { it.id }) { link ->
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
