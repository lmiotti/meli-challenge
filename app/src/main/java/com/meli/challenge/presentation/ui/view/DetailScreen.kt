package com.meli.challenge.presentation.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meli.challenge.domain.model.Cocktail
import com.meli.challenge.presentation.ui.component.ErrorDialog
import com.meli.challenge.presentation.ui.intent.DetailIntent
import com.meli.challenge.presentation.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    name: String,
    onBackPressed: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val handleIntent = { intent: DetailIntent ->
        if (intent is DetailIntent.OnBackPressed) {
            onBackPressed()
        } else {
            viewModel.handleIntent(intent)
        }
    }
    if (state.showError) {
        ErrorDialog {
            handleIntent(DetailIntent.OnDialogDismissClicked)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(name) },
                navigationIcon = {
                    IconButton(onClick = {
                        handleIntent(DetailIntent.OnBackPressed)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        state.cocktail?.let {
            DetailScreenContent(
                paddingValues = paddingValues,
                cocktail = it
            )
        }?.run {

        }
    }
}

@Composable
fun DetailScreenContent(
    paddingValues: PaddingValues,
    cocktail: Cocktail
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){

    }
}