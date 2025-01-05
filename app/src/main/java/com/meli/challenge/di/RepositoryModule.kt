package com.meli.challenge.di

import com.meli.challenge.data.network.repository.CocktailRepository
import com.meli.challenge.data.network.repository.CocktailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCocktailRepository(cocktailRepositoryImpl: CocktailRepositoryImpl): CocktailRepository
}
