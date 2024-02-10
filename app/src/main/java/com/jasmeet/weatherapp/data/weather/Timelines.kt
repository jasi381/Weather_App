package com.jasmeet.weatherapp.data.weather

data class Timelines(
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val minutely: List<Minutely>
)