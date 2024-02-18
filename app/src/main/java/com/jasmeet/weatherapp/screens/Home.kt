package com.jasmeet.weatherapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jasmeet.weatherapp.R
import com.jasmeet.weatherapp.utils.Utils
import com.jasmeet.weatherapp.viewModel.MainViewModel
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    latitude: String,
    longitude: String,
    navController: NavHostController
) {

    val vm: MainViewModel = viewModel()
    val list = vm.apiResponse.observeAsState()
    val isLoading = vm.isLoading.observeAsState()
    val context = androidx.compose.ui.platform.LocalContext.current

    val hazeState = remember {
        HazeState()
    }


    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    val backGroundImage =
        if (Utils.getDayOrNight() == "Day") R.drawable.img_day else R.drawable.img_night

    LaunchedEffect(key1 = Unit, block = {
        vm.fetchDataWithCity("Delhi")
    }
    )



    Scaffold () { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        )
        {
            Image(
                painter = painterResource(id = backGroundImage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )


            if (isLoading.value == true) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier
                        .size(50.dp)
                )
            }

            Column(
                Modifier
                    .wrapContentSize()
                    .padding(innerPadding)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(8.dp))


            }

            BtmSheet(Modifier.align(Alignment.BottomCenter),hazeState = hazeState)

        }
    }


}


@Composable
fun BtmSheet(modifier: Modifier, hazeState: HazeState) {
    Surface(
        modifier = modifier
            .offset(y = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight(fraction = .35f)
            .hazeChild(
                state = hazeState,
                shape = MaterialTheme.shapes.extraLarge,
            ),
        shape = MaterialTheme.shapes.extraLarge,
        color = Color.White.copy(alpha = 0.2f),
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(2.dp, Color.LightGray)
    ) {

        val code = 61
        val condition = Utils.mapWeatherCodeToDescriptionAndIcon(code)
        Text(text = condition.icon)

    }
}