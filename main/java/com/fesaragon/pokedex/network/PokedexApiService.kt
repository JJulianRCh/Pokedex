package com.fesaragon.pokedex.network

import com.fesaragon.pokedex.entity.PokemonDetails
import com.fesaragon.pokedex.entity.PokemonResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


private const val BASE_URL = "https://pokeapi.co/api/v2/"

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface PokedexApiService {

    @GET("pokemon")
    suspend fun getPokemon(@Query ("limit") limit: String = "251"): PokemonResponse

    @GET
    suspend fun getPokemonDetails(@Url url: String): PokemonDetails
}

object PokedexApi {
    val retrofitService: PokedexApiService by lazy {
        retrofit.create(PokedexApiService::class.java)
    }
}