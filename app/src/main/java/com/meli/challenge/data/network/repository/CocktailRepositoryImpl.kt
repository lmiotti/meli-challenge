package com.meli.challenge.data.network.repository

import com.meli.challenge.data.network.model.CocktailListApiResponse
import com.meli.challenge.data.network.service.CocktailService
import com.meli.challenge.models.Resource
import com.meli.challenge.utils.NetworkUtils
import javax.inject.Inject

class CocktailRepositoryImpl @Inject constructor(
    private val cocktailService: CocktailService
): CocktailRepository {
    override suspend fun getCocktailByName(name: String): Resource<CocktailListApiResponse> {
        return NetworkUtils.safeApiCall { cocktailService.getCocktailByName(name) }
    }

    override suspend fun getCocktailByFirstLetter(firstLetter: String): Resource<CocktailListApiResponse> {
        return NetworkUtils.safeApiCall { cocktailService.getCocktailByFirstLetter(firstLetter) }
    }

    override suspend fun getCocktailDetail(id: Long): Resource<CocktailListApiResponse> {
        return NetworkUtils.safeApiCall { cocktailService.getCocktailDetails(id) }
    }
}