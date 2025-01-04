package com.meli.challenge.data.network.repository

import com.meli.challenge.data.network.model.CocktailListApiResponse
import com.meli.challenge.domain.model.Resource

interface CocktailRepository {

    suspend fun getCocktailByName(name: String): Resource<CocktailListApiResponse>
    suspend fun getCocktailByFirstLetter(firstLetter: String): Resource<CocktailListApiResponse>
    suspend fun getCocktailDetail(id: String): Resource<CocktailListApiResponse>
}
