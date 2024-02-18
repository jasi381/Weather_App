package com.jasmeet.weatherapp.utils

import android.content.Context
import android.location.Geocoder
import com.jasmeet.weatherapp.R
import java.util.Calendar
import java.util.Locale

object Utils {
fun getCityNameFromLocation(context: Context, latitude:Double,longitude:Double): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
    return if (addresses!!.isNotEmpty()) {
        addresses[0].locality ?: "Unknown City"
    } else {
        "Unknown City"
    }
}
    fun getDayOrNight(): String {
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        return if (hour in 6..18) {
            "Day"
        } else {
            "Night"
        }
    }


    data class WeatherCondition(val code: Int, val description: String, val icon: Int)

    fun mapWeatherCodeToDescriptionAndIcon(code: Int): WeatherCondition {
        return when (code) {
            0 -> WeatherCondition(0, "Clear sky", R.drawable.img_clear_sky)
            in 1..3 -> WeatherCondition(code, "Mainly clear, partly cloudy, and overcast", R.drawable.img_partly_cloudy)
            in listOf(45, 48) -> WeatherCondition(code, "Fog and depositing rime fog", R.drawable.img_fog)
            in listOf(51, 53, 55) -> WeatherCondition(code, "Drizzle: Light, moderate, and dense intensity", R.drawable.img_drizzle)
            in listOf(56, 57) -> WeatherCondition(code, "Freezing Drizzle: Light and dense intensity", R.drawable.img_snow_drizzle)
            in listOf(61, 63, 65) -> WeatherCondition(code, "Rain: Slight, moderate and heavy intensity", R.drawable.img_rain)
            in listOf(66, 67) -> WeatherCondition(code, "Freezing Rain: Light and heavy intensity", R.drawable.img_snow)
            in listOf(71, 73, 75) -> WeatherCondition(code, "Snow fall: Slight, moderate, and heavy intensity", R.drawable.img_snowfall)
            77 -> WeatherCondition(77, "Snow grains", R.drawable.img_snowgrain)
            in listOf(80, 81, 82) -> WeatherCondition(code, "Rain showers: Slight, moderate, and violent", R.drawable.img_thunderstorm)
            in listOf(85, 86) -> WeatherCondition(code, "Snow showers slight and heavy", R.drawable.img_heavysnowfall)
            in listOf(95, 96, 99) -> WeatherCondition(code, "Thunderstorm: Slight or moderate", R.drawable.img_thunderstorm)
            else -> WeatherCondition(code, "Unknown", R.drawable.img_unknown)
        }
    }

    fun main() {
        val code = 61
        val condition = mapWeatherCodeToDescriptionAndIcon(code)
        println("Weather description for code ${condition.code} is: ${condition.description}")
        println("Icon for the weather: ${condition.icon}")
    }


}