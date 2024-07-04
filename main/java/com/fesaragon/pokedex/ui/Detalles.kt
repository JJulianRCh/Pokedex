package com.fesaragon.pokedex.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.fesaragon.pokedex.entity.Pokemon
import com.fesaragon.pokedex.viewmodel.PokedexViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun DetallesScreen(navController: NavController?, url: String, viewModel: PokedexViewModel) {
    val coroutineScope = rememberCoroutineScope()


    val lifecycleOwner = LocalLifecycleOwner.current
    val state = lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(state) {
        coroutineScope.launch {
            viewModel.getPokemonDetail(url)
        }
    }

    val pokemonDetail = viewModel.pokemonDetails.value

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                Text(text = "Detalles", color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Red)
            )
        }
    ){innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .padding(40.dp)
                .fillMaxWidth(),
        ) {
            pokemonDetail?.let {
                val index = url.split("/").last { it.isNotEmpty() }
                GlideImage(
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$index.png",
                    contentDescription = "",
                    modifier = Modifier.size(250.dp).fillMaxWidth().align(alignment = Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "name: ${it.name?.replaceFirstChar { char -> char.uppercase() }}", color = Color.Black, fontSize = 18.sp)
                Text(text = "number: $index", color = Color.Black, fontSize = 18.sp)
                Text(text = "Base Experience: ${it.base_experience}", color = Color.Black, fontSize = 18.sp)
                Text(text = "weigt: ${it.weight}", color = Color.Black, fontSize = 18.sp)
                Text(text = "height: ${it.height}", color = Color.Black, fontSize = 18.sp)
                Text(text = "type: ${it.types?.joinToString{ it.type?.name?: ""}}", color = Color.Black, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                val container = Pokemon(it.name, url)
                if (viewModel.favorites.value.contains(container)) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.removeFromFavorites(container)
                            navController?.popBackStack()
                        },
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(text = "Eliminar de favoritos", color = Color.White, fontSize = 18.sp)
                    }
                } else {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.addFavorite(container)
                            navController?.popBackStack()
                        },
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(text = "Agregar a favoritos", color = Color.White, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
