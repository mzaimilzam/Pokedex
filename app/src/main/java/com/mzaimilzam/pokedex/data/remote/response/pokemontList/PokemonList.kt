package com.mzaimilzam.pokedex.data.remote.response.pokemontList

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)