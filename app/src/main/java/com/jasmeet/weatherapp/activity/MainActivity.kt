package com.jasmeet.weatherapp.activity

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.graphics.alpha
import androidx.navigation.compose.rememberNavController
import com.jasmeet.weatherapp.R
import com.jasmeet.weatherapp.navigator.MainApp
import com.jasmeet.weatherapp.screens.SplashScreen
import com.jasmeet.weatherapp.theme.WeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT,Color.TRANSPARENT),
            statusBarStyle = SystemBarStyle.light(this.getColor(R.color.black),this.getColor(R.color.black))
        )
        setContent {
            WeatherTheme {
                val navHostController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MainApp(navController = navHostController)
                }
            }
        }
    }
}