package com.meli.challenge.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.meli.challenge.R
import com.meli.challenge.domain.model.Cocktail

@Composable
fun CocktailCard(
    cocktail: Cocktail,
    onCocktailClicked: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(0.625f)
            .padding(vertical = 5.dp)
            .background(Color.White)
            .clickable {
               onCocktailClicked(cocktail.id)
            },
        border = BorderStroke(0.5.dp, Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier.background(Color.White)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.FillWidth,
                painter = rememberAsyncImagePainter(cocktail.thumbnail),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp)
            ) {
                Text(
                    cocktail.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = "Ingredients",
                    style = MaterialTheme.typography.titleMedium
                )
                CocktailCardIngredientGrid(ingredients = cocktail.ingredients)
            }

            val image = if (cocktail.isAlcoholic) {
                R.drawable.ic_alcohol
            } else {
                R.drawable.ic_no_alcohol
            }
            Icon(
                modifier = Modifier.padding(5.dp),
                painter = painterResource(id = image),
                contentDescription = null
            )
        }
    }
}

@Composable
fun CocktailCardIngredientGrid(ingredients: List<String>) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(top = 10.dp),
        columns = GridCells.Fixed(2),
        userScrollEnabled = false
    ) {
        items(ingredients.take(4).size) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .size(4.dp)
                        .background(Color.Black, shape = CircleShape),
                )

                if (ingredients.size > 4 && it == 3) {
                    Text(
                        "More...",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                    )
                } else {
                    Text(
                        ingredients[it],
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}
