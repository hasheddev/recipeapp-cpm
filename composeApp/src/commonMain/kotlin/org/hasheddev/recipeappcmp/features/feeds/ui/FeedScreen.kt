package org.hasheddev.recipeappcmp.features.feeds.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem
import org.hasheddev.recipeappcmp.features.common.ui.ErrorContent
import org.hasheddev.recipeappcmp.features.common.ui.Loader
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FeedRoute(
    navigateToDetail: (Long) -> Unit,
    isUserLoggedIn: () -> Boolean,
    openLogInSheet: (() -> Unit) -> Unit,
    navigateToSearch: () -> Unit,
    feedViewModel: FeedViewModel = koinViewModel()
) {
    val feedUiState = feedViewModel.feedUiState.collectAsState()

    FeedScreen(
        feedUiState = feedUiState.value,
        navigateToSearch = navigateToSearch,
        navigateToDetail = navigateToDetail
    )
}



@Composable
private fun FeedScreen(
    feedUiState: FeedUiState,
    navigateToSearch: () -> Unit,
    navigateToDetail: (Long) -> Unit,
) {
    val recipeList = feedUiState.recipeList

    Scaffold(
        topBar = {
            TopBar(navigateToSearch)
        }
    ) { innerPadding ->
        when {
            feedUiState.isLoading -> { Loader() }
            feedUiState.recipeError != null -> { ErrorContent() }
            recipeList != null -> { FeedContent(
                padding = innerPadding,
                recipes = recipeList,
                navigateToDetail = navigateToDetail
            ) }
        }
    }
}

@Composable
fun FeedContent(
    padding: PaddingValues,
    recipes: List<RecipeItem>,
    navigateToDetail: (Long) -> Unit,
) {
   LazyVerticalGrid(
       verticalArrangement = Arrangement.spacedBy(8.dp),
       columns = GridCells.Fixed(2),
       horizontalArrangement = Arrangement.spacedBy(8.dp),
       modifier = Modifier.padding(
           top = padding.calculateTopPadding()
       )
   ) {
       item(
           span = { GridItemSpan(maxLineSpan) }
       ) {
           TopRecipeList(
               title = "Top Recommendations",
               recipes = recipes.reversed(),
               navigateToDetail = navigateToDetail
           )
       }

       recipeOfTheWeek(
           recipes = recipes,
           title = "Recipes of the week",
           navigateToDetail = navigateToDetail
       )
   }
}

@Composable
private fun TopBar(
    navigateToSearch: () -> Unit,
) {
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars) //we need to consume status bars padding here, as we are not doing it on the NavHost becoz from there it will apply to all tab screen
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Hi User!",
            color = MaterialTheme.colorScheme.primaryContainer,
            style =  MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Got a tasty dish in mind",
            color = MaterialTheme.colorScheme.onBackground,
            style =  MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        SearchBar(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 16.dp)
                .height(45.dp)
                .background(
                    MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(12.dp)
                ).border(
                    width = 1.dp,
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                ).padding(
                    horizontal = 16.dp
                ).clickable { navigateToSearch() }
        )
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
            Spacer(modifier = modifier.width(8.dp))
            Text(
                text = "Search anu recipes",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
            )
        }
    }
}

private fun LazyGridScope.recipeOfTheWeek(
    title: String,
    recipes: List<RecipeItem>,
    navigateToDetail: (Long) -> Unit,
) {
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 10.sp
            ),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
    }

    itemsIndexed(recipes, key = { _, it ->
        it.id
    }) { index, recipe ->
        val cardPaddingStart = if(index % 2 == 0) 16.dp else 0.dp
        val cardPaddingEnd = if(index % 2 == 0) 0.dp else 16.dp
        RecipeCard(
            recipe,
            modifier = Modifier.width(120.dp)
                .padding(
                    start = cardPaddingStart,
                    end = cardPaddingEnd
                ),
            imageModifier = Modifier.fillMaxWidth().height(130.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    navigateToDetail(recipe.id)
                }
        )
    }

}

@Composable
fun TopRecipeList(
    title: String,
    recipes: List<RecipeItem>,
    navigateToDetail: (Long) -> Unit,
) {
    Column() {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 10.sp
            ),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recipes, key = {
                it.id
            }) { recipe ->
                RecipeCard(
                    recipe,
                    modifier = Modifier.width(120.dp),
                    imageModifier = Modifier.width(120.dp).height(140.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            navigateToDetail(recipe.id)
                        }
                )
            }
        }
    }
}

@Composable
fun RecipeCard(
    recipe: RecipeItem,
    modifier: Modifier,
    imageModifier: Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = recipe.imageUrl,
            onError = {
                println("On error ${it.result.throwable}")
            },
            modifier = imageModifier,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        Text(
            textAlign = TextAlign.Start,
            text = recipe.title,
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = recipe.duration,
                    modifier = Modifier.padding(start = 4.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                )

                Text(
                    text = "${recipe.rating}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp),
                )
            }
        }

    }
}

