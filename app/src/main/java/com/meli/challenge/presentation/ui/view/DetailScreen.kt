package com.meli.challenge.presentation.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.meli.challenge.R
import com.meli.challenge.domain.model.Cocktail
import com.meli.challenge.presentation.Constants.Details.IMAGE_ASPECT_RATIO
import com.meli.challenge.presentation.Constants.Loading.LOADING_ITEMS
import com.meli.challenge.presentation.ui.component.AlcoholIcon
import com.meli.challenge.presentation.ui.component.BulletItem
import com.meli.challenge.presentation.ui.component.ErrorDialog
import com.meli.challenge.presentation.ui.intent.DetailIntent
import com.meli.challenge.presentation.viewmodel.DetailViewModel
import com.valentinilk.shimmer.shimmer

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
        } ?: LoadingDetail(paddingValues)
    }
}

@Composable
fun DetailScreenContent(
    paddingValues: PaddingValues,
    cocktail: Cocktail
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        item {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(IMAGE_ASPECT_RATIO)
                    .background(Color.LightGray),
                contentScale = ContentScale.FillWidth,
                painter = rememberAsyncImagePainter(cocktail.thumbnail),
                contentDescription = null
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_xl)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.cocktail_card_ingredients),
                    style = MaterialTheme.typography.headlineSmall
                )
                AlcoholIcon(isAlcoholic = cocktail.isAlcoholic)
            }
        }

        items(cocktail.ingredients.size) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_xl)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BulletItem()
                Text(
                    cocktail.ingredients[it].measure,
                    modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_s)),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    cocktail.ingredients[it].name,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        
        item { 
            Box(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_m)))
        }
    }
}

@Composable
fun LoadingDetail(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shimmer()
                .aspectRatio(IMAGE_ASPECT_RATIO)
                .background(Color.LightGray)
        ) {}

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shimmer()
                .height(80.dp)
                .padding(dimensionResource(id = R.dimen.padding_xl))
        ) {}

        repeat(LOADING_ITEMS) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(
                        vertical = dimensionResource(id = R.dimen.padding_s),
                        horizontal = dimensionResource(id = R.dimen.padding_xl)
                    )
                    .shimmer()
            ) {}
        }
    }
}
