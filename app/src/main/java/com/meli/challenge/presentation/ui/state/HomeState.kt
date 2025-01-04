package com.meli.challenge.presentation.ui.state

import com.meli.challenge.domain.model.Cocktail

data class HomeState(
    val cocktailName: String = "",
    val isLoading: Boolean = false,
    val cocktails: List<Cocktail>? = null,
    val showError: Boolean = false,
) {
    val showWelcomeState: Boolean
        get() = !isLoading && cocktails == null

    val showEmptyState: Boolean
        get() {
            return if (cocktails != null) {
                !isLoading && cocktails.isEmpty()
            } else {
                false
            }
        }
}
