package com.meli.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.meli.challenge.data.network.repository.CocktailRepository
import com.meli.challenge.domain.model.Cocktail
import com.meli.challenge.presentation.ui.component.CocktailCard
import com.meli.challenge.presentation.ui.theme.BootstrapTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: CocktailRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BootstrapTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(100) {
                            val cocktail = Cocktail(
                                name = "Margarita",
                                thumbnail = "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
                                ingredients = listOf("Triple sec", "Salt", "Lime juice", "Tequila"),
                                isAlcoholic = true
                            )
                            CocktailCard(cocktail = cocktail)
                        }
                    }
                }
            }
        }
    }
}
