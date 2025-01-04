package com.meli.challenge.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = DetailViewModel.Factory::class)
class DetailViewModel @AssistedInject constructor(
    @Assisted private val id: String
): ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: String): DetailViewModel
    }

    init {
        Log.e("ASD", "$id")
    }
}