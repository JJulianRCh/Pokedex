package com.fesaragon.pokedex

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fesaragon.pokedex.ui.DetallesScreen
import com.fesaragon.pokedex.ui.FavoritosScreen
import com.fesaragon.pokedex.ui.HomeScreen
import com.fesaragon.pokedex.ui.theme.PokedexTheme
import com.fesaragon.pokedex.viewmodel.PokedexViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokedexTheme {
                Navegation()
            }
        }
    }
}

@Composable
fun Navegation() {
    val navController = rememberNavController()
    val viewModel: PokedexViewModel = viewModel()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController = navController, viewModel = viewModel) }
        composable("detalles/{pokemonURL}") {
            Log.d("Principal", "pasando de pantallas")
            val id = it.arguments?.getString("pokemonURL")?: ""
            val url = "https://pokeapi.co/api/v2/pokemon/$id/"
            DetallesScreen(navController = navController, url = url, viewModel = viewModel)
        }
        composable("favoritos") { FavoritosScreen(navController = navController, viewModel = viewModel) }
    }
}
