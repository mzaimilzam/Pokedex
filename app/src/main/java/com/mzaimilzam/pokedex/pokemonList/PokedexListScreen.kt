package com.mzaimilzam.pokedex.pokemonList

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.bitmap.BitmapPool
import coil.compose.rememberImagePainter
import coil.transform.Transformation
import com.mzaimilzam.pokedex.R
import com.mzaimilzam.pokedex.models.PokedexListEntry

/**
 * Created by Muhammad Zaim Milzam on 03/10/2021.
 * linkedin : Muhammad Zaim Milzam
 */

@ExperimentalCoilApi
@Composable
fun PokedexListScreen(
    navController: NavController,
    viewModel: PokedexListViewModels = hiltViewModel()

) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                viewModel.searchPokemonList(it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    Box(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(26.dp))
                .clip(AbsoluteRoundedCornerShape(20.dp)),
            value = text,
            placeholder = {
                Text(
                    text = hint,
                    color = Color.Gray
                )
            },
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = Color.Black,
            )
        )
    }
}

@ExperimentalCoilApi
@Composable
fun PokemonList(
    navController: NavController,
    viewModel: PokedexListViewModels = hiltViewModel()
) {
    val pokemontList by remember { viewModel.pokemonList }
    val endReach by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if (pokemontList.size % 2 == 0) {
            pokemontList.size / 2
        } else {
            pokemontList.size / 2 + 1
        }

        items(itemCount) {
            if (it >= itemCount - 1 && !endReach && !isLoading && !isSearching) {
                viewModel.loadPokemonPaginated()
            }
            PokedexRow(rowIndex = it, entries = pokemontList, navController = navController)
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        if (loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadPokemonPaginated()
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun PokedexEntry(
    entry: PokedexListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokedexListViewModels = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colors.surface.toArgb()
    var dominantColor by rememberSaveable {
        mutableStateOf(defaultDominantColor)
    }
    var isLoading by remember { mutableStateOf(true) }

    Box(
        contentAlignment = Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(dominantColor),
                        Color(defaultDominantColor),
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "pokemon_detail_screen/${dominantColor}/${entry.pokedexName}"
                )
            }
    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
            ) {
                if (isLoading)
                    CircularProgressIndicator(modifier = Modifier.align(Center))

                Image(
                    painter = rememberImagePainter(
                        data = entry.imageUrl,
                        builder = {
                            crossfade(true)
                            transformations(
                                object : Transformation {
                                    override fun key(): String {
                                        return entry.imageUrl
                                    }

                                    override suspend fun transform(
                                        pool: BitmapPool,
                                        input: Bitmap,
                                        size: coil.size.Size
                                    ): Bitmap {
                                        viewModel.calculateDominatColor(input) { color ->
                                            dominantColor = color.toArgb()
                                            isLoading = false
                                        }
                                        return input
                                    }
                                }
                            )
                        }
                    ),
                    contentDescription = entry.pokedexName,
                    modifier = Modifier
                        .size(120.dp)
                )
            }

            Text(
                text = entry.pokedexName,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@ExperimentalCoilApi
@Composable
fun PokedexRow(
    rowIndex: Int,
    entries: List<PokedexListEntry>,
    navController: NavController
) {
    Column {
        Row {
            PokedexEntry(
                entry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (entries.size >= rowIndex * 2 + 2) {
                PokedexEntry(
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}
