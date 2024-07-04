package com.fesaragon.pokedex.repository

import com.fesaragon.pokedex.network.PokedexApi

class PokedexRepository {
    suspend fun getPokemon() = PokedexApi.retrofitService.getPokemon()
    suspend fun getPokemonDetails(url: String) = PokedexApi.retrofitService.getPokemonDetails(url)
}