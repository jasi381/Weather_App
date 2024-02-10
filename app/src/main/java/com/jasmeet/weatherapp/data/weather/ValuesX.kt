package com.jasmeet.weatherapp.data.weather

data class ValuesX(
    val cloudBase: Double,
    val cloudCeiling: Double,
    val cloudCover: Double,
    val dewPoFloat: Double,
    val evapotranspiration: Double,
    val freezingRainFloatensity: Float,
    val humidity: Double,
    val iceAccumulation: Float,
    val iceAccumulationLwe: Float,
    val precipitationProbability: Float,
    val pressureSurfaceLevel: Double,
    val rainAccumulation: Double,
    val rainAccumulationLwe: Double,
    val rainFloatensity: Double,
    val sleetAccumulation: Float,
    val sleetAccumulationLwe: Float,
    val sleetFloatensity: Float,
    val snowAccumulation: Double,
    val snowAccumulationLwe: Double,
    val snowDepth: Float,
    val snowFloatensity: Double,
    val temperature: Double,
    val temperatureApparent: Double,
    val uvHealthConcern: Float,
    val uvIndex: Float,
    val visibility: Double,
    val weatherCode: Float,
    val windDirection: Double,
    val windGust: Double,
    val windSpeed: Double
)