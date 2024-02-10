package com.jasmeet.weatherapp.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.*
import com.jasmeet.weatherapp.R
import com.jasmeet.weatherapp.data.location.LocationDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavHostController) {

    var locationCallBack: LocationCallback? = null
    var fusedLocationClient: FusedLocationProviderClient? = null
    val locationRequired = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var currentLocation by remember {
        mutableStateOf(LocationDetails(0.toDouble(), 0.toDouble()))
    }

    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            locationRequired.value = true
            startLocationUpdates(locationCallBack, fusedLocationClient)
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    locationCallBack = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            for (lo in p0.locations) {
                currentLocation = LocationDetails(lo.latitude, lo.longitude)
                if (lo.latitude != 0.0 && lo.longitude != 0.0) {
                    // Navigate to the home screen when a valid location is received
                    navigateToHome(navController, currentLocation,coroutineScope)
                    // Remove location updates as we've received a valid location
                    fusedLocationClient.removeLocationUpdates(this)
                    return
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        if (permissions.all {
                ContextCompat.checkSelfPermission(
                    context,
                    it
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            startLocationUpdates(locationCallBack, fusedLocationClient)
        } else {
            launcherMultiplePermissions.launch(permissions)
        }
    }

    Column(
        Modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xff67669d),
                        Color(0xFFE0E0E0)
                    )
                )
            )
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.img_splash), contentDescription = null, modifier = Modifier.size(150.dp))
        CircularProgressIndicator()
    }
}

@SuppressLint("MissingPermission")
fun startLocationUpdates(
    locationCallBack: LocationCallback?,
    fusedLocationClient: FusedLocationProviderClient?,
    timeInterval: Long = 1000,
) {
    locationCallBack?.let {
        val locationRequest = LocationRequest.Builder(PRIORITY_HIGH_ACCURACY, timeInterval).apply {
            setMinUpdateDistanceMeters(0.0f)
            setGranularity(GRANULARITY_PERMISSION_LEVEL)
            setWaitForAccurateLocation(true)
        }.build()
        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            it,
            Looper.getMainLooper()
        )
    }
}

fun navigateToHome(
    navController: NavHostController,
    location: LocationDetails,
    coroutineScope: CoroutineScope
) {
    coroutineScope.launch {
        delay(2000)
        val navOptions = NavOptions.Builder()
            .setPopUpTo(Screen.Splash.route, inclusive = true)
            .build()

        navController.navigate(Screen.Home.createRoute(location.latitude, location.longitude), navOptions)
    }
}
