package com.fesaragon.pokedex.entity

import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse (
    val results: List<Pokemon>?
)

@Serializable
data class Pokemon(
    val name: String?,
    val url: String?
)

@Serializable
data class PokemonDetails(
    val name: String?,
    val base_experience: Int?,
    val weight: Int?,
    val height: Int?,
    val types: List<TypeSlot>?
)

@Serializable
data class TypeSlot(
    val slot: Int?,
    val type: Type?
)

@Serializable
data class Type(
    val name: String?
)