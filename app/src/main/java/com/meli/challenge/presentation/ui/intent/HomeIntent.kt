package com.meli.challenge.presentation.ui.intent

sealed class HomeIntent {
    data class OnSearchTextChanged(val name: String): HomeIntent()
    data class OnCocktailClicked(val id: String): HomeIntent()
    data object OnSearchClicked: HomeIntent()
    data object OnDialogDismissClicked: HomeIntent()
}
