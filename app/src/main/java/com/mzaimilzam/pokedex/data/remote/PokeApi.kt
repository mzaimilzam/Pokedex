package com.mzaimilzam.pokedex.data.remote

import com.mzaimilzam.pokedex.data.remote.response.pokemon.Pokemon
import com.mzaimilzam.pokedex.data.remote.response.pokemontList.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Muhammad Zaim Milzam on 03/10/2021.
 * linkedin : Muhammad Zaim Milzam
 */
interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): PokemonList


    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): Pokemon
}