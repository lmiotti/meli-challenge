package com.meli.challenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.challenge.domain.model.Cocktail
import com.meli.challenge.domain.usecase.GetCocktailUseCase
import com.meli.challenge.models.Resource
import com.meli.challenge.presentation.ui.intent.HomeIntent
import com.meli.challenge.presentation.ui.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailViewModel @Inject constructor(
    private val useCase: GetCocktailUseCase
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>
        get() = _state

    fun handleIntent(intent: HomeIntent) {
        when(intent) {
            is HomeIntent.OnSearchTextChanged -> _state.update { it.copy(cocktailName = intent.name) }
            is HomeIntent.OnDialogDismissClicked ->
                _state.update { it.copy(showError = false) }
            is HomeIntent.OnSearchClicked -> searchCocktail()
        }
    }

    private fun searchCocktail() {
        viewModelScope.launch {
            useCase(state.value.cocktailName).collectLatest { result ->
                when(result) {
                    is Resource.Loading ->
                        _state.update { it.copy(isLoading = true) }
                    is Resource.Success ->
                        _state.update { it.copy(
                            isLoading = false,
                            cocktails = result.data ?: listOf()
                        )
                    }
                    is Resource.Failure -> {
                        _state.update { it.copy(isLoading = false, showError = true) }
                    }
                }
            }
        }
    }
}
