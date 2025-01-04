package com.meli.challenge.viewmodel

import app.cash.turbine.test
import com.meli.challenge.BaseCoroutineTest
import com.meli.challenge.domain.model.Cocktail
import com.meli.challenge.domain.usecase.GetCocktailUseCase
import com.meli.challenge.models.NetworkError
import com.meli.challenge.models.Resource
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
        // Given
        val cocktail = "Margarita"

        // When
        viewModel.handleIntent(HomeIntent.OnSearchTextChanged(cocktail))
        dispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.state.test {
            Assert.assertEquals(HomeState().copy(cocktailName = cocktail), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
    @Test
    fun whenOnSearchClicked_thenStateIsLoading() = runTest {
        // Given
        val cocktail = "Margarita"
        Mockito.doReturn(flowOf(Resource.Loading<Unit>())).`when`(useCase).invoke(cocktail)

        // When
        viewModel.handleIntent(HomeIntent.OnSearchTextChanged(cocktail))
        viewModel.handleIntent(HomeIntent.OnSearchClicked)

        // Then
        viewModel.state.test {
            Assert.assertEquals(HomeState().copy(cocktailName = cocktail, isLoading = true), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenOnSearchClickedAndResourceIsSuccess_thenShowCocktailList() = runTest {
        // Given
        val cocktailName = "Margarita"
        val cocktailList = listOf(
            Cocktail(
                id = "11007",
                name = "Margarita",
                thumbnail = "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
                ingredients = listOf("Tequila", "Triple sec", "Lime juice", "Salt"),
                isAlcoholic = true
            ),
            Cocktail(
                id = "11118",
                name = "Blue Margarita",
                thumbnail = "https://www.thecocktaildb.com/images/media/drink/bry4qh1582751040.jpg",
                ingredients = listOf("Tequila", "Blue Curacao", "Lime juice", "Salt"),
                isAlcoholic = true
            )
        )
        Mockito.doReturn(flowOf(Resource.Success(cocktailList))).`when`(useCase).invoke(cocktailName)

        // When
        viewModel.handleIntent(HomeIntent.OnSearchTextChanged(cocktailName))
        viewModel.handleIntent(HomeIntent.OnSearchClicked)

        // Then
        viewModel.state.test {
            Assert.assertEquals(HomeState().copy(cocktailName = cocktailName, cocktails = cocktailList), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenOnSearchClickedAndResourceIsFailure_thenShowErrorIsTrue() = runTest {
        // Given
        val cocktailName = "Margarita"
        val networkError = NetworkError(code = 400, message = "Not Found")
        Mockito.doReturn(flowOf(Resource.Failure<List<Cocktail>>(networkError))).`when`(useCase).invoke(cocktailName)

        // When
        viewModel.handleIntent(HomeIntent.OnSearchTextChanged(cocktailName))
        viewModel.handleIntent(HomeIntent.OnSearchClicked)

        // Then
        viewModel.state.test {
            Assert.assertEquals(HomeState().copy(cocktailName = cocktailName, showError = true), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenOnDialogDismissClicked_thenShowErrorIsFalse() = runTest {
        // Given
        val cocktailName = "Margarita"
        val networkError = NetworkError(code = 400, message = "Not Found")
        Mockito.doReturn(flowOf(Resource.Failure<List<Cocktail>>(networkError))).`when`(useCase).invoke(cocktailName)

        // When
        viewModel.handleIntent(HomeIntent.OnSearchTextChanged(cocktailName))
        viewModel.handleIntent(HomeIntent.OnSearchClicked)

        // Then
        viewModel.state.test {
            Assert.assertEquals(HomeState().copy(cocktailName = cocktailName, showError = true), awaitItem())
            viewModel.handleIntent(HomeIntent.OnDialogDismissClicked)
            Assert.assertEquals(HomeState().copy(cocktailName = cocktailName, showError = false), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}