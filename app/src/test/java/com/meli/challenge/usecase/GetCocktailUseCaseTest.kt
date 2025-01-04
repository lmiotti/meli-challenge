package com.meli.challenge.usecase

import app.cash.turbine.test
import com.meli.challenge.BaseCoroutineTest
import com.meli.challenge.FakeValues.fakeCocktailListApiResponse
import com.meli.challenge.FakeValues.fakeError
import com.meli.challenge.FakeValues.fakeFirstLetter
import com.meli.challenge.FakeValues.fakeName
import com.meli.challenge.data.network.model.CocktailListApiResponse
import com.meli.challenge.data.network.repository.CocktailRepository
import com.meli.challenge.domain.model.Cocktail
import com.meli.challenge.domain.usecase.GetCocktailUseCase
import com.meli.challenge.models.Resource
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class GetCocktailUseCaseTest: BaseCoroutineTest() {

    @Mock
    private lateinit var repository: CocktailRepository

    private lateinit var useCase: GetCocktailUseCase

    override fun afterSetup() {
        super.afterSetup()
        useCase = GetCocktailUseCase(repository, dispatcher, dispatcher)
    }

    @Test
    fun whenUseCaseIsInvokedAndNameIsFirstLetterAndResourceIsSuccess_thenCocktailIsShown() = runTest {
        // Given
        Mockito.doReturn(Resource.Success(fakeCocktailListApiResponse)).`when`(repository).getCocktailByFirstLetter(fakeFirstLetter)

        // When
        val response = useCase.invoke(fakeFirstLetter)

        // Then
        response.test {
            Assert.assertEquals(Resource.Loading<List<Cocktail>>(), awaitItem())
            Assert.assertEquals(Resource.Success(fakeCocktailListApiResponse.cocktails?.map { it.toCocktail() }), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenUseCaseIsInvokedAndNameIsCompleteAndResourceIsSuccess_thenCocktailIsShown() = runTest {
        // Given
        Mockito.doReturn(Resource.Success(fakeCocktailListApiResponse)).`when`(repository).getCocktailByName(fakeName)

        // When
        val response = useCase.invoke(fakeName)

        // Then
        response.test {
            Assert.assertEquals(Resource.Loading<List<Cocktail>>(), awaitItem())
            Assert.assertEquals(Resource.Success(fakeCocktailListApiResponse.cocktails?.map { it.toCocktail() }), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenUseCaseIsInvokedAndResourceIsFailure_thenErrorIsShown() = runTest {
        // Given
        Mockito.doReturn(Resource.Failure<CocktailListApiResponse>(fakeError)).`when`(repository).getCocktailByName(fakeName)

        // When
        val response = useCase.invoke(fakeName)

        // Then
        response.test {
            Assert.assertEquals(Resource.Loading<List<Cocktail>>(), awaitItem())
            Assert.assertEquals(Resource.Failure<List<Cocktail>>(fakeError), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
