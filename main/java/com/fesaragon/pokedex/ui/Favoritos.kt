package com.fesaragon.pokedex.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.fesaragon.pokedex.viewmodel.PokedexViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun FavoritosScreen(navController: NavController?, viewModel: PokedexViewModel) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Favoritos", color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Red
                )
            )
        }
    ) {innerPadding ->
        LazyColumn (
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .fillMaxWidth()
        ) {
            items(viewModel.favorites.value) {
                val index = it.url?.split("/")?.last { it.isNotEmpty() }
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