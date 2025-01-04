package com.meli.challenge.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {

    @Serializable
    data object Home: Routes()

    @Serializable
    data class Detail(
        val id: String,
        val name: String
    ): Routes()
}