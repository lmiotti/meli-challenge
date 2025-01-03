package com.meli.challenge.domain.model

data class Cocktail(
    val name: String,
    val thumbnail: String,
    val ingredients: List<String>,
    val isAlcoholic: Boolean
)