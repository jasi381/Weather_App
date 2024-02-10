package com.jasmeet.weatherapp.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jasmeet.weatherapp.screens.Home
import com.jasmeet.weatherapp.screens.LATITUDE
import com.jasmeet.weatherapp.screens.LONGITUDE
import com.jasmeet.weatherapp.screens.Screen
import com.jasmeet.weatherapp.screens.SplashScreen

@Composable
fun MainApp(navController: NavHostController) {


    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(
            route = Screen.Home.route,
            arguments = listOf(
                navArgument(LATITUDE){
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                },
                navArgument(LONGITUDE){
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                }
            )
        ){
                Home(navController = navController, latitude = it.arguments?.getString(LATITUDE).toString(), longitude = it.arguments?.getString(LONGITUDE).toString())


        }
    }
}