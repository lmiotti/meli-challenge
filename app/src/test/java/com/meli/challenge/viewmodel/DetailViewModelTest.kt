package com.meli.challenge.viewmodel

import app.cash.turbine.test
import com.meli.challenge.BaseCoroutineTest
import com.meli.challenge.FakeValues.fakeCocktail
import com.meli.challenge.FakeValues.fakeError
import com.meli.challenge.FakeValues.fakeId
import com.meli.challenge.domain.model.Cocktail
import com.meli.challenge.domain.usecase.GetDetailsUseCase
import com.meli.challenge.domain.model.Resource
import com.meli.challenge.presentation.ui.intent.DetailIntent
import com.meli.challenge.presentation.ui.intent.HomeIntent
import com.meli.challenge.presentation.ui.state.DetailState
import com.meli.challenge.presentation.viewmodel.DetailViewModel
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
        Mockito.doReturn(flowOf(Resource.Loading<Unit>())).`when`(useCase).invoke(fakeId)

        // When
        viewModel = DetailViewModel(fakeId, useCase)

        // Then
        viewModel.state.test {
            Assert.assertEquals(DetailState().copy(isLoading = true), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenViewModelInitsAndResourceIsSuccess_thenCocktailIsShown() = runTest {
        // Given
        Mockito.doReturn(flowOf(Resource.Success(fakeCocktail))).`when`(useCase).invoke(fakeId)

        // When
        viewModel = DetailViewModel(fakeId, useCase)

        // Then
        viewModel.state.test {
            Assert.assertEquals(DetailState().copy(isLoading = false, cocktail = fakeCocktail), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenViewModelInitsAndResourceIsFailure_thenShowErrorIsTrue() = runTest {
        // Given
        Mockito.doReturn(flowOf(Resource.Failure<Cocktail>(fakeError))).`when`(useCase).invoke(fakeId)

        // When
        viewModel = DetailViewModel(fakeId, useCase)

        // Then
        viewModel.state.test {
            Assert.assertEquals(DetailState().copy(isLoading = false, showError = true), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenOnDialogDismissClicked_thenShowErrorIsFalse() = runTest {
        // Given
        Mockito.doReturn(flowOf(Resource.Failure<Cocktail>(fakeError))).`when`(useCase).invoke(fakeId)

        // When
        viewModel = DetailViewModel(fakeId, useCase)

        // Then
        viewModel.state.test {
            Assert.assertEquals(DetailState().copy(isLoading = false, showError = true), awaitItem())
            viewModel.handleIntent(DetailIntent.OnDialogDismissClicked)
            Assert.assertEquals(DetailState().copy(isLoading = false, showError = false), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenOnBackPressed_thenNavigate() = runTest {
        // Given
        Mockito.doReturn(flowOf(Resource.Success(fakeCocktail))).`when`(useCase).invoke(fakeId)

        // When
        viewModel = DetailViewModel(fakeId, useCase)

        viewModel.navigate.test {
            viewModel.handleIntent(DetailIntent.OnBackPressed)

            // Then
            Assert.assertEquals(Unit, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
