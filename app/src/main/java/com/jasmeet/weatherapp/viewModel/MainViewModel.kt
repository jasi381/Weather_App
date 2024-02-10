package com.jasmeet.weatherapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jasmeet.weatherapp.data.weather.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class MainViewModel :ViewModel(){

    companion object {
        private const val BASE_URL = "https://api.tomorrow.io/v4/weather/forecast"
        private const val API_KEY = "wZV9POuUwTcLg1UwFE5C6y9qBg9vZaFc"
    }

    private val _apiResult = MutableLiveData<String>()
    val apiResult: LiveData<String> get() = _apiResult

    private val _apiResponse = MutableLiveData<WeatherData>()
    val apiResponse: LiveData<WeatherData> get() = _apiResponse


    fun fetchDataWithCoordinates(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = makeApiCall("$BASE_URL?location=$lat,$lon&apikey=$API_KEY")
            _apiResult.postValue(result)
            Log.d("MainViewModel", "fetchData: $result")
            parseApiResponse(result)
        }
    }

    fun fetchDataWithCity(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = makeApiCall("$BASE_URL?location=$city&apikey=$API_KEY")
            _apiResult.postValue(result)
            Log.d("MainViewModel", "fetchDataCity: $result")
            parseApiResponse(result)
        }
    }

    private fun parseApiResponse(response: String) {
        val gson = Gson()
        val apiResponse = gson.fromJson(response, WeatherData::class.java)
        _apiResponse.postValue(apiResponse)
    }

    //make api call using try catch block
    private fun makeApiCall(url: String): String {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        return try {
            val response = client.newCall(request).execute()
            response.body?.string() ?: ""
        } catch (e: IOException) {
            _apiResult.postValue(e.message)
            e.printStackTrace()
            ""
        }
    }

}