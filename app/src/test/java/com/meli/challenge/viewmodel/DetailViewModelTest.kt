package com.meli.challenge.viewmodel

import app.cash.turbine.test
import com.meli.challenge.BaseCoroutineTest
import com.meli.challenge.domain.model.Cocktail
import com.meli.challenge.domain.usecase.GetDetailsUseCase
import com.meli.challenge.models.NetworkError
import com.meli.challenge.models.Resource
import com.meli.challenge.presentation.ui.intent.DetailIntent
import com.meli.challenge.presentation.ui.intent.HomeIntent
import com.meli.challenge.presentation.ui.state.DetailState
import com.meli.challenge.presentation.ui.state.HomeState
import com.meli.challenge.presentation.viewmodel.DetailViewModel
import com.meli.challenge.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest: BaseCoroutineTest() {

    @Mock
    private lateinit var useCase: GetDetailsUseCase

    private lateinit var viewModel: DetailViewModel

    @Test
    fun whenViewModelInits_thenIsLoadingIsTrue() = runTest {
        // Given
        val id = "11007"
        Mockito.doReturn(flowOf(Resource.Loading<Unit>())).`when`(useCase).invoke(id)

        // When
        viewModel = DetailViewModel(id, useCase)

        // Then
        viewModel.state.test {
            Assert.assertEquals(DetailState().copy(isLoading = true), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenViewModelInitsAndResourceIsSuccess_thenCocktailIsShown() = runTest {
        // Given
        val cocktail = Cocktail(
            id = "11007",
            name = "Margarita",
            thumbnail = "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
            ingredients = listOf("Tequila", "Triple sec", "Lime juice", "Salt"),
            isAlcoholic = true
        )
        Mockito.doReturn(flowOf(Resource.Success(cocktail))).`when`(useCase).invoke(cocktail.id)

        // When
        viewModel = DetailViewModel(cocktail.id, useCase)

        // Then
        viewModel.state.test {
            Assert.assertEquals(DetailState().copy(isLoading = false, cocktail = cocktail), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenViewModelInitsAndResourceIsFailure_thenShowErrorIsTrue() = runTest {
        // Given
        val id = "11007"
        val networkError = NetworkError(code = 400, message = "Not Found")
        Mockito.doReturn(flowOf(Resource.Failure<Cocktail>(networkError))).`when`(useCase).invoke(id)

        // When
        viewModel = DetailViewModel(id, useCase)

        // Then
        viewModel.state.test {
            Assert.assertEquals(DetailState().copy(isLoading = false, showError = true), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenOnDialogDismissClicked_thenShowErrorIsFalse() = runTest {
        // Given
        val id = "11007"
        val networkError = NetworkError(code = 400, message = "Not Found")
        Mockito.doReturn(flowOf(Resource.Failure<Cocktail>(networkError))).`when`(useCase).invoke(id)

        // When
        viewModel = DetailViewModel(id, useCase)

        // Then
        viewModel.state.test {
            Assert.assertEquals(DetailState().copy(isLoading = false, showError = true), awaitItem())
            viewModel.handleIntent(DetailIntent.OnDialogDismissClicked)
            Assert.assertEquals(DetailState().copy(isLoading = false, showError = false), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}