package com.meli.challenge.presentation.ui.intent

import com.meli.challenge.domain.model.Cocktail

sealed class HomeIntent {
    data class OnSearchTextChanged(val name: String): HomeIntent()
    data class OnCocktailClicked(val cocktail: Cocktail): HomeIntent()
    data object OnSearchClicked: HomeIntent()
    data object OnDialogDismissClicked: HomeIntent()
    data object OnClearClicked: HomeIntent()
}
