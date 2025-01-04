package com.meli.challenge.presentation.ui.state

import com.meli.challenge.domain.model.Cocktail

data class DetailState(
    val isLoading: Boolean = false,
    val showError: Boolean = false,
    val cocktail: Cocktail? = null
)