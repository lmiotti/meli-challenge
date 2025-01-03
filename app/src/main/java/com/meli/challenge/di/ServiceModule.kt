package com.meli.challenge.di

import com.meli.challenge.data.network.service.CocktailService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideServiceModule(retrofit: Retrofit): CocktailService {
        return retrofit.create(CocktailService::class.java)
    }
}