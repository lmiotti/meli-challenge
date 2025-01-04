package com.meli.challenge.data.network.service

import com.meli.challenge.data.network.model.CocktailListApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailService {

    @GET("/api/json/v1/1/search.php")
    suspend fun getCocktailByName(
        @Query("s") name: String
    ): Response<CocktailListApiResponse>

    @GET("/api/json/v1/1/search.php")
    suspend fun getCocktailByFirstLetter(
        @Query("f") firstLetter: String
    ): Response<CocktailListApiResponse>

    @GET("/api/json/v1/1/lookup.php")
    suspend fun getCocktailDetails(
        @Query("i") id: String
    ): Response<CocktailListApiResponse>
}