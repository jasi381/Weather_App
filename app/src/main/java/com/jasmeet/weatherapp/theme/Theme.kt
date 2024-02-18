package com.jasmeet.weatherapp.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    //color for button
    primary = darkButtonColor,
    //color for background
    background = darkBackGround,
    //color for text
    onPrimary = darkButtonTextColor,
    onSurface = darkTextColor

)

private val LightColorScheme = lightColorScheme(
    //color for button
    primary = lightButtonColor,
    //color for background
    background = lightBackground,
    //color for text
    onPrimary = lightButtonTextColor ,
    onSurface = lightTextColor

)

@Composable
fun WeatherTheme(
    darkTheme: Boolean= false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity  = view.context as Activity
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(activity.window, view).isAppearanceLightNavigationBars = false
            }
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}