package com.meli.challenge.presentation.ui.view

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.meli.challenge.presentation.ui.component.CocktailCard
import com.meli.challenge.presentation.ui.component.SearchTextField
import com.meli.challenge.presentation.ui.intent.HomeIntent
import com.meli.challenge.presentation.ui.state.HomeState
import com.meli.challenge.presentation.viewmodel.CocktailViewModel
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: CocktailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.showError.collectLatest {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    Scaffold(
        topBar = {
            SearchTextField(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .padding(horizontal = 10.dp),
                value = state.cocktailName,
                onValueChanged = { viewModel.handleIntent(HomeIntent.OnSearchTextChanged(it)) },
                onSearchClicked = { viewModel.handleIntent(HomeIntent.OnSearchClicked) }
            )
        }
    ) { paddingValues ->
        HomeScreenContent(paddingValues, state, { viewModel.handleIntent(it) })
    }

}

@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues,
    state: HomeState,
    handleIntent: (HomeIntent) -> Unit
) {
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
            items(state.cocktails.size) {
                CocktailCard(cocktail = state.cocktails[it])
            }
        }

    }
}