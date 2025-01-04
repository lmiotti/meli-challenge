package com.meli.challenge.domain.usecase

import com.meli.challenge.data.network.repository.CocktailRepository
import com.meli.challenge.di.DefaultDispatcher
import com.meli.challenge.di.IoDispatcher
import com.meli.challenge.domain.model.Cocktail
import com.meli.challenge.domain.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(
    private val repository: CocktailRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(id: String): Flow<Resource<Cocktail>> = flow {
        emit(Resource.Loading())
        emit(repository.getCocktailDetail(id))
    }
    .flowOn(ioDispatcher)
    .map {
        when(it) {
            is Resource.Success -> {
                val cocktails = it.data?.cocktails?.map { it.toCocktail() } ?: listOf()
                Resource.Success(cocktails.first())
            }
            is Resource.Failure -> Resource.Failure(networkError = it.error!!)
            is Resource.Loading -> Resource.Loading()
        }
    }
    .flowOn(defaultDispatcher)
}
