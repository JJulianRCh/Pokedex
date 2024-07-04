package com.fesaragon.pokedex.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fesaragon.pokedex.entity.Pokemon
import com.fesaragon.pokedex.entity.PokemonDetails
import com.fesaragon.pokedex.repository.PokedexRepository
import kotlinx.coroutines.launch

class PokedexViewModel(private val pokedexRepository: PokedexRepository = PokedexRepository()): ViewModel() {

    var pokemons = mutableStateOf(emptyList<Pokemon>())
    var pokemonDetails = mutableStateOf<PokemonDetails?>(null)
    var favorites = mutableStateOf(emptyList<Pokemon>())

    fun getPokemons() {
        viewModelScope.launch {
            val response = pokedexRepository.getPokemon()
            response.results?.let {
                pokemons.value = it
            }
        }
    }

    fun getPokemonDetail(url: String) {
        viewModelScope.launch {
            val response = pokedexRepository.getPokemonDetails(url)
            response.let {
                pokemonDetails.value = it
            }
        }
    }

    fun addFavorite(pokemon: Pokemon) {
        favorites.value += pokemon
    }

    fun removeFromFavorites(pokemon: Pokemon) {
        val mutableList = favorites.value.toMutableList()
        mutableList.remove(pokemon)
        favorites.value = mutableList.toList()
    }
}