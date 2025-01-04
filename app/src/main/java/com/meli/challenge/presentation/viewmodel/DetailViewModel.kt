package com.meli.challenge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.challenge.domain.usecase.GetDetailsUseCase
import com.meli.challenge.models.Resource
import com.meli.challenge.presentation.ui.intent.DetailIntent
import com.meli.challenge.presentation.ui.state.DetailState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailViewModel.Factory::class)
class DetailViewModel @AssistedInject constructor(
    @Assisted private val id: String,
    private val useCase: GetDetailsUseCase
): ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(id: String): DetailViewModel
    }

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState>
        get() = _state

    init {
        viewModelScope.launch {
            useCase(id).collectLatest { response ->
                when(response) {
                    is Resource.Loading ->
                        _state.update { it.copy(isLoading = true) }
                    is Resource.Success ->
                        _state.update { it.copy(isLoading = false, cocktail = response.data) }
                    is Resource.Failure ->
                        _state.update { it.copy(isLoading = false, showError = true) }
                }
            }
        }
    }

    fun handleIntent(intent: DetailIntent) {
        if (intent is DetailIntent.OnDialogDismissClicked) {
            _state.update { it.copy(showError = false) }
        }
    }
}