package com.meli.challenge.viewmodel

import app.cash.turbine.test
import com.meli.challenge.BaseCoroutineTest
import com.meli.challenge.FakeValues.fakeCocktailList
import com.meli.challenge.FakeValues.fakeError
import com.meli.challenge.FakeValues.fakeName
import com.meli.challenge.domain.model.Cocktail
import com.meli.challenge.domain.usecase.GetCocktailUseCase
import com.meli.challenge.domain.model.Resource
import com.meli.challenge.presentation.ui.intent.HomeIntent
import com.meli.challenge.presentation.ui.state.HomeState
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
class HomeViewModelTest: BaseCoroutineTest() {

    @Mock
    private lateinit var useCase: GetCocktailUseCase

    private lateinit var viewModel: HomeViewModel

    override fun afterSetup() {
        super.afterSetup()
        viewModel = HomeViewModel(useCase)
    }

    @Test
    fun whenSearchTextChanged_thenCocktailNameIsUpdated() = runTest {
        // When
        viewModel.handleIntent(HomeIntent.OnSearchTextChanged(fakeName))

        // Then
        viewModel.state.test {
            Assert.assertEquals(HomeState().copy(cocktailName = fakeName), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenOnClearClicked_thenCocktailNameIsEmpty() = runTest {
        // When
        viewModel.handleIntent(HomeIntent.OnSearchTextChanged(fakeName))

        // Then
        viewModel.state.test {
            Assert.assertEquals(HomeState().copy(cocktailName = fakeName), awaitItem())
        }

        // When
        viewModel.handleIntent(HomeIntent.OnClearClicked)

        // Then
        viewModel.state.test {
            Assert.assertEquals(HomeState().copy(cocktailName = ""), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenOnSearchClicked_thenStateIsLoading() = runTest {
        // Given
        Mockito.doReturn(flowOf(Resource.Loading<Unit>())).`when`(useCase).invoke(fakeName)

        // When
        viewModel.handleIntent(HomeIntent.OnSearchTextChanged(fakeName))
        viewModel.handleIntent(HomeIntent.OnSearchClicked)

        // Then
        viewModel.state.test {
            Assert.assertEquals(HomeState().copy(cocktailName = fakeName, isLoading = true), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenOnSearchClickedAndResourceIsSuccess_thenShowCocktailList() = runTest {
        // Given
        Mockito.doReturn(flowOf(Resource.Success(fakeCocktailList))).`when`(useCase).invoke(fakeName)

        // When
        viewModel.handleIntent(HomeIntent.OnSearchTextChanged(fakeName))
        viewModel.handleIntent(HomeIntent.OnSearchClicked)

        // Then
        viewModel.state.test {
            Assert.assertEquals(HomeState().copy(cocktailName = fakeName, cocktails = fakeCocktailList), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenOnSearchClickedAndResourceIsFailure_thenShowErrorIsTrue() = runTest {
        // Given
        Mockito.doReturn(flowOf(Resource.Failure<List<Cocktail>>(fakeError))).`when`(useCase).invoke(fakeName)

        // When
        viewModel.handleIntent(HomeIntent.OnSearchTextChanged(fakeName))
        viewModel.handleIntent(HomeIntent.OnSearchClicked)

        // Then
        viewModel.state.test {
            Assert.assertEquals(HomeState().copy(cocktailName = fakeName, showError = true), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenOnDialogDismissClicked_thenShowErrorIsFalse() = runTest {
        // Given
        Mockito.doReturn(flowOf(Resource.Failure<List<Cocktail>>(fakeError))).`when`(useCase).invoke(fakeName)

        // When
        viewModel.handleIntent(HomeIntent.OnSearchTextChanged(fakeName))
        viewModel.handleIntent(HomeIntent.OnSearchClicked)

        // Then
        viewModel.state.test {
            Assert.assertEquals(HomeState().copy(cocktailName = fakeName, showError = true), awaitItem())
            viewModel.handleIntent(HomeIntent.OnDialogDismissClicked)
            Assert.assertEquals(HomeState().copy(cocktailName = fakeName, showError = false), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
