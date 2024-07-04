package com.fesaragon.pokedex.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.fesaragon.pokedex.R
import com.fesaragon.pokedex.ui.theme.PokedexTheme
import com.fesaragon.pokedex.viewmodel.PokedexViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun HomeScreen(navController: NavController?, viewModel: PokedexViewModel) {

    val coroutineScope = rememberCoroutineScope()

    val lifecycleOwner = LocalLifecycleOwner.current
    val state = lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(state) {
        when (state.value) {
            Lifecycle.State.STARTED -> {
                coroutineScope.launch {
                    viewModel.getPokemons()
                }
            }
            else -> {}
        }
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.pokedex_logo),
                        contentDescription = "logo de pokedex"
                    )
                        },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController?.navigate("favoritos") },
                containerColor = Color.Red,
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier.size(72.dp)
            ) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "Favoritos",
                    tint = Color.Yellow,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .fillMaxWidth()
        ) {
            items(viewModel.pokemons.value) {
                Log.d("Home", "${viewModel.pokemons.value}")
                val index = viewModel.pokemons.value.indexOf(it) + 1
                Card (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
                    onClick = {
                        navController?.navigate("detalles/${index}")
                    }
                ) {
                    Row (verticalAlignment = Alignment.CenterVertically) {
                        GlideImage(
                            modifier = Modifier.size(100.dp),
                            model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$index.png",
                            contentDescription = ""
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = "${it.name?.replaceFirstChar { char -> char.uppercase() }}", color = Color.Black)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val viewModel: PokedexViewModel = viewModel()
    PokedexTheme {
        HomeScreen(navController = null, viewModel = viewModel)
    }
}