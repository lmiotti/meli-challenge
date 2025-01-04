package com.meli.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.meli.challenge.data.network.repository.CocktailRepository
import com.meli.challenge.presentation.navigation.NavManager
import com.meli.challenge.presentation.ui.theme.BootstrapTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class  MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: CocktailRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BootstrapTheme {
                NavManager()
            }
        }
    }
}
