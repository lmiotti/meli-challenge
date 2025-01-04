package com.meli.challenge.usecase

import app.cash.turbine.test
import com.meli.challenge.BaseCoroutineTest
import com.meli.challenge.FakeValues.fakeCocktailListApiResponse
import com.meli.challenge.FakeValues.fakeError
import com.meli.challenge.FakeValues.fakeId
import com.meli.challenge.data.network.model.CocktailListApiResponse
import com.meli.challenge.data.network.repository.CocktailRepository
import com.meli.challenge.domain.model.Cocktail
import com.meli.challenge.domain.usecase.GetDetailsUseCase
import com.meli.challenge.domain.model.Resource
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetDetailUseCaseTest : BaseCoroutineTest() {


    @Mock
    private lateinit var repository: CocktailRepository

    private lateinit var useCase: GetDetailsUseCase

    override fun afterSetup() {
        super.afterSetup()
        useCase = GetDetailsUseCase(repository, dispatcher, dispatcher)
    }

    @Test
    fun whenUseCaseIsInvokedAndResourceIsSuccess_thenCocktailIsShown() = runTest {
        // Given
        Mockito.doReturn(Resource.Success(fakeCocktailListApiResponse)).`when`(repository).getCocktailDetail(fakeId)

        // When
        val response = useCase.invoke(fakeId)

        // Then
        response.test {
            Assert.assertEquals(Resource.Loading<List<Cocktail>>(), awaitItem())
            Assert.assertEquals(Resource.Success(fakeCocktailListApiResponse.cocktails?.map { it.toCocktail() }?.first()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun whenUseCaseIsInvokedAndResourceIsFailure_thenErrorIsShown() = runTest {
        // Given
        Mockito.doReturn(Resource.Failure<CocktailListApiResponse>(fakeError)).`when`(repository).getCocktailDetail(fakeId)

        // When
        val response = useCase.invoke(fakeId)

        //Then
        response.test {
            Assert.assertEquals(Resource.Loading<List<Cocktail>>(), awaitItem())
            Assert.assertEquals(Resource.Failure<List<Cocktail>>(fakeError), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
