package com.meli.challenge.presentation.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.meli.challenge.R

@Composable
fun AlcoholIcon(isAlcoholic: Boolean) {
    val image = if (isAlcoholic) {
        R.drawable.ic_alcohol
    } else {
        R.drawable.ic_no_alcohol
    }
    Icon(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_s)),
        painter = painterResource(id = image),
        contentDescription = null
    )
}