package com.mzaimilzam.pokedex.di

import com.mzaimilzam.pokedex.data.remote.PokeApi
import com.mzaimilzam.pokedex.repository.PokemonRepository
import com.mzaimilzam.pokedex.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Muhammad Zaim Milzam on 03/10/2021.
 * linkedin : Muhammad Zaim Milzam
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi
    ) = PokemonRepository(api)

    @Singleton
    @Provides
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }
}