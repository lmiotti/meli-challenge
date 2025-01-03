package com.meli.challenge.domain.usecase

import com.meli.challenge.data.network.repository.CocktailRepository
import com.meli.challenge.di.DefaultDispatcher
import com.meli.challenge.di.IoDispatcher
import com.meli.challenge.domain.model.Cocktail
import com.meli.challenge.models.NetworkError
import com.meli.challenge.models.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCocktailUseCase @Inject constructor(
    private val repository: CocktailRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(name: String): Flow<Resource<List<Cocktail>>> = flow {
        emit(Resource.Loading())
        when {
            name.isEmpty() -> emit(Resource.Failure(NetworkError(message = "Please, enter a cocktail")))
            name.length == 1 -> emit(repository.getCocktailByFirstLetter(name))
            else -> emit(repository.getCocktailByName(name))
        }
    }
    .flowOn(ioDispatcher)
    .map {
        when(it) {
            is Resource.Success -> {
                val cocktails = it.data?.cocktails?.map { it.toCocktail() } ?: listOf()
                Resource.Success(cocktails)
            }
            is Resource.Failure -> Resource.Failure(networkError = it.error!!)
            is Resource.Loading -> Resource.Loading()
        }
    }
    .flowOn(defaultDispatcher)
}