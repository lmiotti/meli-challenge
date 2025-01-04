package com.meli.challenge.domain.model

data class Cocktail(
    val id: String,
    val name: String,
    val thumbnail: String,
    val ingredients: List<Ingredient>,
    val isAlcoholic: Boolean
)
