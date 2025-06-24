package org.hasheddev.recipeappcmp.features.detail.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.hasheddev.recipeappcmp.features.common.data.api.mapper.capitalizeFirstWord
import org.hasheddev.recipeappcmp.features.common.domain.entities.RecipeItem
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailRoute(
    recipeId: Long,
    onBackClick: () -> Unit,
    isUserLoggedIn: () -> Boolean,
    openLogInSheet: (() -> Unit) -> Unit,
    viewModel: RecipeDetailViewModel = koinViewModel()
) {
    LaunchedEffect(true) {
        viewModel.getRecipeDetail(recipeId)
    }

    var showDialogue by remember { mutableStateOf(false) }

    val detailUiState by viewModel.detailState.collectAsStateWithLifecycle()

    val uriHandler = LocalUriHandler.current
    val onWatchVideoClick: (String) -> Unit = {
        if(it.isNotEmpty()) {
            uriHandler.openUri(it)
        }
    }

    val onSaveClick: (RecipeItem) -> Unit = {
        if(!isUserLoggedIn()) { showDialogue = true }

        else { viewModel.updateIsFavorite(id = it.id, isAdding = !it.isFavorite) }

    }


    val updateIsFavoriteState = viewModel.updateIsFavoriteState.collectAsStateWithLifecycle()
    if (showDialogue) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.background,
            onDismissRequest =  { showDialogue = false },
            title = {
                Text(
                    text = "update favorites",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Text(text = "Login To Add/Remove Favorites")
            },
            confirmButton = {
                Button(colors = ButtonDefaults.buttonColors().copy(
                    contentColor = MaterialTheme.colorScheme.primaryContainer
                ),
                    onClick = {
                        showDialogue = false
                        openLogInSheet {
                            detailUiState.recipeDetail?.let{
                                viewModel.updateIsFavorite(
                                    id = it.id,
                                    isAdding = !it.isFavorite
                                )
                            }
                        }
                    }
                ) {
                    Text("Login")
                }
            },
            dismissButton = {
                OutlinedButton(
                    border = BorderStroke(1.dp,  MaterialTheme.colorScheme.primaryContainer),
                    colors = ButtonDefaults.buttonColors().copy(
                    contentColor = MaterialTheme.colorScheme.background
                ),
                    onClick = {
                        showDialogue = false
                    }
                ) {
                    Text("Cancel", color = MaterialTheme.colorScheme.onBackground)
                }
            }
        )
    }
    DetailScreen(
        detailUiState,
        updateIsFavoriteState.value,
        onBackClick,
        onWatchVideoClick,
        onSaveClick
    )
}


@Composable
private fun  DetailScreen(
    detailUiState: RecipeDetailUiState,
    updateIsFavorite: RecipeDetailUpdateIsFavorite,
    onBackClick: () -> Unit,
    onWatchVideoClick: (String) -> Unit,
    onSaveClick: (RecipeItem) -> Unit
) {
    Scaffold(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            when {
                detailUiState.isLoading -> { LoadingScreen() }
                detailUiState.errorDetail != null -> {
                    ErrorScreen(detailUiState.errorDetail, onBackClick)
                }
                detailUiState.recipeDetail != null -> {
                    RecipeDetailContent(
                        detailUiState.recipeDetail,
                        onBackClick,
                        onWatchVideoClick,
                        onSaveClick
                    )
                }
            }
        }
    }
}

@Composable
fun RecipeDetailContent(
    recipeDetail: RecipeItem,
    onBackClick: () -> Unit,
    onWatchVideoClick: (String) -> Unit,
    onSaveClick: (RecipeItem) -> Unit,
) {
   Box(
       modifier = Modifier.fillMaxSize()
   ) {
       RecipeMainContent(recipeDetail, onWatchVideoClick)
       Row(
           horizontalArrangement = Arrangement.SpaceBetween,
           modifier = Modifier.fillMaxWidth().padding(WindowInsets.statusBars.asPaddingValues())
               .padding(vertical = 32.dp).padding(horizontal = 16.dp)
               .align(Alignment.TopCenter)
       ) {
           IconButton(
               onClick = onBackClick,
               modifier = Modifier.padding(horizontal = 8.dp)
                   .size(30.dp)
                   .background(
                       MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                       shape = CircleShape
                   ),
           ){
               Icon(
                   imageVector = Icons.AutoMirrored.Default.ArrowBack,
                   contentDescription = null,
                   tint = MaterialTheme.colorScheme.onBackground
               )
           }

           IconButton(
               onClick = {
                   onSaveClick(recipeDetail)
               },
               modifier = Modifier.padding(horizontal = 8.dp)
                   .size(30.dp)
                   .background(
                       MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                       shape = CircleShape
                   ),
           ){
               Icon(
                   imageVector = if(recipeDetail.isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                   contentDescription = null,
                   tint = MaterialTheme.colorScheme.onBackground
               )
           }
       }
   }
}

@Composable
fun RecipeMainContent(
    recipeDetail: RecipeItem,
    onWatchVideoClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = recipeDetail.imageUrl,
            contentDescription = recipeDetail.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(250.dp).clip(
                RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
        )

        RecipeDetails(recipeDetail)

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = recipeDetail.description,
                style = MaterialTheme.typography.bodySmall
            )

            IngredientsList(
                ingredients = recipeDetail.ingredients.map {
                    val item = it.split(":")
                    if (item.isNotEmpty() && item.size == 2) {
                        Pair(item[0].trim().capitalizeFirstWord(), item[1].trim())
                    } else {
                        Pair("", "")
                    }
                }.filter {
                    it.first.isNotEmpty() && it.second.isNotEmpty()
                }.filterNot {
                    it.first.contains("null") || it.second.contains("null")
                }
            )

            InstructionsList(recipeDetail.instructions)
            WatchVideoButton(
                youtubeLink = recipeDetail.youtubeLink,
                onWatchVideoClick =  onWatchVideoClick
            )
        }
    }
}

@Composable
fun IngredientsList(
    ingredients: List<Pair<String, String>>
){
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Ingredients",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        ingredients.forEach { IngredientItem(name = it.first, quantity = it.second) }
    }
}

@Composable
fun InstructionsList(
    instructions: List<String>
){
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Instructions",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        instructions.forEachIndexed { index, it ->
            Text(
                text = "${index + 1} $it",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun WatchVideoButton(
    youtubeLink: String,
    onWatchVideoClick: (String) -> Unit
){
    Button(
        onClick = {
        onWatchVideoClick(youtubeLink)
        },
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "watch",
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Watch Video",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}

@Composable
fun IngredientItem(name: String, quantity: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            text = quantity,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun RecipeDetails(
    recipeItem: RecipeItem
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = recipeItem.title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
    }

    Row(
        modifier = Modifier.padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Schedule,
            modifier = Modifier.size(16.dp),
            contentDescription = null
        )
        Text(
            text = recipeItem.description,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            imageVector = Icons.Default.Star,
            modifier = Modifier.size(16.dp),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        )
        Text(
            text = "${recipeItem.rating} stars",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = recipeItem.difficulty,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
fun ErrorScreen(
    errorMessage: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick) {
            Text("Go Back")
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
