package com.jasmeet.weatherapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jasmeet.weatherapp.viewModel.MainViewModel

@Composable
fun Home(latitude: String, longitude: String, navController: NavHostController) {

    val vm :MainViewModel= viewModel()

    LaunchedEffect(key1 = Unit, block = {
        vm.fetchDataWithCoordinates(latitude.toDouble(), longitude.toDouble())
        vm.fetchDataWithCity("cairo")
    })
    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Latitude: $latitude")
        Text(text = "Longitude: $longitude")
        Button(onClick = {

        }) {
            Text(text = "Get Weather")
        }

    }
    
}