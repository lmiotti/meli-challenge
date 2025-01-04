package com.meli.challenge.presentation.ui.intent

sealed class DetailIntent {

    data object OnBackPressed: DetailIntent()
    data object OnDialogDismissClicked: DetailIntent()
}
