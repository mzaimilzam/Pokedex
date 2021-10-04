package com.mzaimilzam.pokedex.repository

import com.mzaimilzam.pokedex.data.remote.PokeApi
import com.mzaimilzam.pokedex.data.remote.response.pokemon.Pokemon
import com.mzaimilzam.pokedex.data.remote.response.pokemontList.PokemonList
import com.mzaimilzam.pokedex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

/**
 * Created by Muhammad Zaim Milzam on 03/10/2021.
 * linkedin : Muhammad Zaim Milzam
 */

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
) {

    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("An Unkown error occured.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonDetail(name: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonDetail(name)
        } catch (e: Exception) {
            return Resource.Error("An Unkown error occured.")
        }
        return Resource.Success(response)
    }


}