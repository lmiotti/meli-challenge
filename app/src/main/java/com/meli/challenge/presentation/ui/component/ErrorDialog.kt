package com.meli.challenge.presentation.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.meli.challenge.R

@Composable
fun ErrorDialog(onDismissClicked: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissClicked,
        title = { Text(text = stringResource(id = R.string.error)) },
        text = { Text(text = stringResource(id = R.string.try_again)) },
        confirmButton = {
            Button(
                onClick = onDismissClicked
            ) {
                Text(
                    text = stringResource(id = R.string.ok),
                    color = Color.White
                )
            }
        }
    )
}