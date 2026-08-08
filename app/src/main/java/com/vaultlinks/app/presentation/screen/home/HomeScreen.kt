package com.vaultlinks.app.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.outlined.Storage
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vaultlinks.app.domain.model.Category
import com.vaultlinks.app.domain.model.Link
import com.vaultlinks.app.presentation.components.EmptyState
import com.vaultlinks.app.presentation.components.LinkCard
import com.vaultlinks.app.presentation.components.StatCard
import com.vaultlinks.app.presentation.theme.CategoryPalette
import com.vaultlinks.app.presentation.theme.VaultViolet
import com.vaultlinks.app.util.DateUtils
import androidx.compose.foundation.lazy.LazyColumn

@Composable
fun HomeScreen(
    onLinkClick: (Long) -> Unit,
    onSaveClick: () -> Unit,
    onCategoryClick: (Long) -> Unit,
    onCollectionClick: (Long) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onSaveClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                text = { Text("Quick Save") }
            )
        }
    ) { padding ->
        if (!state.isLoading && state.recentSaves.isEmpty() && state.pinnedLinks.isEmpty()) {
            EmptyState(
                icon = Icons.Outlined.Bookmarks,
                title = "Your vault is empty",
                subtitle = "Save your first link with the button below, or share straight into VaultLinks from any app.",
                modifier = Modifier.padding(padding)
            )
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Welcome back", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text("Your Vault", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
                }
            }

            state.stats?.let { stats ->
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            label = "Total Saves", value = stats.totalLinks.toString(),
                            icon = Icons.Outlined.Bookmarks, accentColor = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            label = "Today", value = stats.savedToday.toString(),
                            icon = Icons.Outlined.Today, accentColor = Color(0xFF00B894),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            label = "This Week", value = stats.savedThisWeek.toString(),
                            icon = Icons.Outlined.CalendarToday, accentColor = Color(0xFFE17055),
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            label = "Storage Used", value = DateUtils.formatStorage(stats.estimatedStorageBytes),
                            icon = Icons.Outlined.Storage, accentColor = Color(0xFF0984E3),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            if (state.pinnedLinks.isNotEmpty()) {
                item { SectionHeader(title = "Pinned Links", icon = Icons.Outlined.PushPin) }
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(state.pinnedLinks, key = { it.id }) { link ->
                            Box(modifier = Modifier.width(220.dp)) {
                                LinkCard(
                                    link = link,
                                    categoryLabel = categoryLabelFor(link, state.categories),
                                    categoryColor = categoryColorFor(link, state.categories),
                                    onClick = { onLinkClick(link.id) },
                                    onFavoriteClick = { viewModel.toggleFavorite(link) }
                                )
                            }
                        }
                    }
                }
                item { Spacer(Modifier.height(8.dp)) }
            }

            if (state.categories.isNotEmpty()) {
                item { SectionHeader(title = "Categories", icon = Icons.Outlined.Folder) }
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(state.categories.take(12), key = { it.id }) { category ->
                            CategoryPill(category, onClick = { onCategoryClick(category.id) })
                        }
                    }
                }
                item { Spacer(Modifier.height(8.dp)) }
            }

            if (state.recentSaves.isNotEmpty()) {
                item { SectionHeader(title = "Recent Saves", icon = Icons.Outlined.Bookmarks) }
                items(state.recentSaves, key = { it.id }) { link ->
                    Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 6.dp)) {
                        LinkCard(
                            link = link,
                            categoryLabel = categoryLabelFor(link, state.categories),
                            categoryColor = categoryColorFor(link, state.categories),
                            onClick = { onLinkClick(link.id) },
                            onFavoriteClick = { viewModel.toggleFavorite(link) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(8.dp))
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun CategoryPill(category: Category, onClick: () -> Unit) {
    val color = runCatching { Color(android.graphics.Color.parseColor(category.colorHex)) }.getOrDefault(VaultViolet)
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = color.copy(alpha = 0.12f),
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 10.dp)
                .clickableCompat(onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(8.dp).background(color, CircleShape))
            Spacer(Modifier.width(8.dp))
            Text(category.name, style = MaterialTheme.typography.labelLarge, color = color)
            Spacer(Modifier.width(6.dp))
            Text("${category.linkCount}", style = MaterialTheme.typography.labelSmall, color = color.copy(alpha = 0.7f))
        }
    }
}

private fun categoryLabelFor(link: Link, categories: List<Category>): String? =
    categories.firstOrNull { it.id == link.categoryId }?.name

private fun categoryColorFor(link: Link, categories: List<Category>): Color {
    val hex = categories.firstOrNull { it.id == link.categoryId }?.colorHex
    return hex?.let { runCatching { Color(android.graphics.Color.parseColor(it)) }.getOrNull() }
        ?: CategoryPalette[(link.id % CategoryPalette.size).toInt()]
}

private fun Modifier.clickableCompat(onClick: () -> Unit): Modifier =
    this.then(Modifier.clickable(onClick = onClick))
