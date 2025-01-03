package com.meli.challenge.data.network.model

import com.google.gson.annotations.SerializedName

data class CocktailListApiResponse(
    @SerializedName("drinks") val cocktails: List<CocktailApiResponse>? = null
)