package com.meli.challenge.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import com.meli.challenge.R
import com.meli.challenge.domain.model.Cocktail
import com.meli.challenge.presentation.Constants.CocktailCard.COCKTAIL_CARD_ASPECT_RATIO
import com.meli.challenge.presentation.Constants.Ingredients.GRID_CELLS
import com.meli.challenge.presentation.Constants.Ingredients.INGREDIENTS_PREVIEW_SIZE
import com.meli.challenge.presentation.Constants.Ingredients.MAX_LINES

@Composable
fun CocktailCard(
    cocktail: Cocktail,
    onCocktailClicked: (String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(COCKTAIL_CARD_ASPECT_RATIO)
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_s)
            )
            .background(MaterialTheme.colorScheme.background)
            .clickable {
                onCocktailClicked(cocktail.id, cocktail.name)
            },
        border = BorderStroke(
            dimensionResource(id = R.dimen.cocktail_card_stroke_width),
            Color.Black
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(id = R.dimen.cocktail_card_elevation)
        )
    ) {
        Column(
            modifier = Modifier.background(Color.White)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(id = R.dimen.cocktail_card_ingredient_image_height))
                    .background(Color.LightGray),
                contentScale = ContentScale.FillWidth,
                painter = rememberAsyncImagePainter(cocktail.thumbnail),
                contentDescription = null,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = dimensionResource(id = R.dimen.padding_m),
                        horizontal = dimensionResource(id = R.dimen.padding_xl)
                    )
            ) {
                Text(
                    cocktail.name,
                    maxLines = MAX_LINES,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.padding_xl)
                    ),
                    text = stringResource(id = R.string.cocktail_card_ingredients),
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )
                CocktailCardIngredientGrid(ingredients = cocktail.ingredients.map { it.name })
            }

            AlcoholIcon(isAlcoholic = cocktail.isAlcoholic)
        }
    }
}

@Composable
fun CocktailCardIngredientGrid(ingredients: List<String>) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.cocktail_card_ingredient_grid_height))
            .padding(dimensionResource(id = R.dimen.padding_m)),
        columns = GridCells.Fixed(GRID_CELLS),
        userScrollEnabled = false
    ) {
        val auxIngredients = ingredients.take(INGREDIENTS_PREVIEW_SIZE)
        items(auxIngredients.size) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding_s)
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BulletItem(Color.Black)

                if (ingredients.size > INGREDIENTS_PREVIEW_SIZE && it == auxIngredients.lastIndex) {
                    Text(
                        stringResource(id = R.string.cocktail_card_more),
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                    )
                } else {
                    Text(
                        ingredients[it],
                        maxLines = MAX_LINES,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}
