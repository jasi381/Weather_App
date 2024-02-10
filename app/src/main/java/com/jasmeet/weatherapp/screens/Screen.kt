package com.jasmeet.weatherapp.screens

const val LATITUDE = "latitude"
const val LONGITUDE = "longitude"

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen(
        "home?latitude={$LATITUDE}&longitude={$LONGITUDE}"
    ){
        fun createRoute(
            latitude: Double,
            longitude: Double
        ): String {
            return this.route
                .replace("{$LATITUDE}", latitude.toString())
                .replace("{$LONGITUDE}", longitude.toString())
        }
    }
    data object Search : Screen("search")
    data object SavedLocation : Screen("savedLocation")
}
