package com.meli.challenge.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.meli.challenge.R

@Composable
fun BulletItem() {
    Box(
        modifier = Modifier
            .padding(
                end = dimensionResource(id = R.dimen.padding_s)
            )
            .size(dimensionResource(id = R.dimen.cocktail_card_ingredient_bullet_size))
            .background(Color.Black, shape = CircleShape),
    )
}