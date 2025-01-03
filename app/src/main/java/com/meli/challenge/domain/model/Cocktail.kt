package com.meli.challenge.domain.model

data class Cocktail(
    val name: String,
    val ingredient1: String?,
    val ingredient2: String?,
    val ingredient3: String?,
    val ingredient4: String?,
    val isAlcoholic: Boolean
)