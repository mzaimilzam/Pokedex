package com.mzaimilzam.pokedex.data.remote.response.pokemon

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)