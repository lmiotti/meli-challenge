package com.meli.challenge.presentation.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.meli.challenge.R
import com.meli.challenge.presentation.Constants
import com.meli.challenge.presentation.ui.component.CocktailCard
import com.meli.challenge.presentation.ui.component.ErrorDialog
import com.meli.challenge.presentation.ui.component.LoadingCard
import com.meli.challenge.presentation.ui.component.SearchTextField
import com.meli.challenge.presentation.ui.intent.HomeIntent
import com.meli.challenge.presentation.ui.state.HomeState
import com.meli.challenge.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onCocktailClicked: (String, String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.navigate.collectLatest {
                onCocktailClicked(it.id, it.name)
            }
        }
    }

    if (state.showError) {
        ErrorDialog {
            viewModel.handleIntent(HomeIntent.OnDialogDismissClicked)
        }
    }

    Scaffold(
        topBar = {
            SearchTextField(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.padding_s))
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_m)),
                value = state.cocktailName,
                onValueChanged = { viewModel.handleIntent(HomeIntent.OnSearchTextChanged(it)) },
                onSearchClicked = { viewModel.handleIntent(HomeIntent.OnSearchClicked) },
                onClearClicked = { viewModel.handleIntent(HomeIntent.OnClearClicked) }
            )
        }
    ) { paddingValues ->
        HomeScreenContent(paddingValues, state) { viewModel.handleIntent(it) }
    }

}

@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues,
    state: HomeState,
    handleIntent: (HomeIntent) -> Unit
) {
    when {
        state.showWelcomeState -> CocktailWelcomeState()
        state.showEmptyState -> CocktailEmptyState()
        else -> LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = dimensionResource(id = R.dimen.padding_m))
                .padding(top = dimensionResource(id = R.dimen.padding_s))
                .background(MaterialTheme.colorScheme.background),
            columns = GridCells.Fixed(Constants.Home.GRID_CELLS),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_m)),
        ) {
            if (state.isLoading) {
                items(Constants.Loading.LOADING_ITEMS) {
                    LoadingCard()
                }
            } else {
                items(state.cocktails!!.size) {
                    CocktailCard(cocktail = state.cocktails[it]) { cocktail ->
                        handleIntent(HomeIntent.OnCocktailClicked(cocktail))
                    }
                }
            }
        }
    }
}

@Composable
fun CocktailWelcomeState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_l)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(dimensionResource(id = R.dimen.home_welcome_image)),
            painter = painterResource(id = R.drawable.ic_drink),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Text(
            stringResource(id = R.string.home_welcome),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun CocktailEmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_l)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(dimensionResource(id = R.dimen.home_not_found_image)),
            painter = painterResource(id = R.drawable.ic_no_drink_found),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Text(
            stringResource(id = R.string.home_not_found),
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.padding_xl)
            ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            stringResource(id = R.string.home_try_again),
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.padding_s)
            ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}
