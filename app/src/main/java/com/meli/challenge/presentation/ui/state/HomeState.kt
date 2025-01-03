package com.meli.challenge.presentation.ui.state

import com.meli.challenge.domain.model.Cocktail

data class HomeState(
    val cocktailName: String = "",
    val isLoading: Boolean = false,
    val cocktails: List<Cocktail> = listOf()
)