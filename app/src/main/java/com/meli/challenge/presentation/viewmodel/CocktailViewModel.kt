package com.meli.challenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.meli.challenge.domain.usecase.GetCocktailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val useCase: GetCocktailUseCase
): ViewModel() {
}