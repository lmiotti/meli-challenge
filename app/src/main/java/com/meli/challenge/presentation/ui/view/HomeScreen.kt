package com.meli.challenge.presentation.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meli.challenge.R
import com.meli.challenge.presentation.ui.component.CocktailCard
import com.meli.challenge.presentation.ui.component.SearchTextField
import com.meli.challenge.presentation.ui.intent.HomeIntent
import com.meli.challenge.presentation.ui.state.HomeState
import com.meli.challenge.presentation.viewmodel.CocktailViewModel
import com.valentinilk.shimmer.shimmer

@Composable
fun HomeScreen(
    viewModel: CocktailViewModel = hiltViewModel(),
    onCocktailClicked: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.showError) {
        NotFoundDialog {
            viewModel.handleIntent(HomeIntent.OnDialogDismissClicked)
        }
    }

    val handleIntent = { intent: HomeIntent ->
        if (intent is HomeIntent.OnCocktailClicked) {
            onCocktailClicked(intent.id)
        } else {
            viewModel.handleIntent(intent)
        }
    }

    Scaffold(
        topBar = {
            SearchTextField(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .padding(horizontal = 10.dp),
                value = state.cocktailName,
                onValueChanged = { handleIntent(HomeIntent.OnSearchTextChanged(it)) },
                onSearchClicked = { handleIntent(HomeIntent.OnSearchClicked) }
            )
        }
    ) { paddingValues ->
        HomeScreenContent(paddingValues, state, handleIntent)
    }

}

@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues,
    state: HomeState,
    handleIntent: (HomeIntent) -> Unit
) {
    if (state.showEmptyState) {
        CocktailEmptyState()
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 10.dp)
                .padding(top = 5.dp)
                .background(Color.White),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            if (state.isLoading) {
                items(10) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.625f)
                            .padding(vertical = 5.dp)
                            .shimmer(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
                    ) {}
                }
            } else {
                items(state.cocktails!!.size) {
                    CocktailCard(cocktail = state.cocktails[it]) {
                        handleIntent(HomeIntent.OnCocktailClicked(it))
                    }
                }
            }
        }
    }
}

@Composable
fun CocktailEmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.ic_drink),
            contentDescription = null
        )
        Text(
            "Please enter a cocktail in the search bar to start using the application",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun NotFoundDialog(onDismissClicked: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissClicked,
        title = { Text(text = "Error") },
        text = { Text(text = "Please, try again later or check your internet connection") },
        confirmButton = { // 6
            Button(
                onClick = onDismissClicked
            ) {
                Text(
                    text = "OK",
                    color = Color.White
                )
            }
        }
    )
}
