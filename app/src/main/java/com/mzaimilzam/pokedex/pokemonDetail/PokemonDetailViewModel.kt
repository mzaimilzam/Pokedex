package com.mzaimilzam.pokedex.pokemonDetail

import androidx.lifecycle.ViewModel
import com.mzaimilzam.pokedex.data.remote.response.pokemon.Pokemon
import com.mzaimilzam.pokedex.repository.PokemonRepository
import com.mzaimilzam.pokedex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by Muhammad Zaim Milzam on 04/10/2021.
 * linkedin : Muhammad Zaim Milzam
 */
@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {


    suspend fun loadPokemonDetail(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonDetail(pokemonName)

    }
}