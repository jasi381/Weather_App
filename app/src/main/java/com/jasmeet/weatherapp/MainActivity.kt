package com.jasmeet.weatherapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jasmeet.weatherapp.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val lat = intent.getStringExtra("lat")
        val long = intent.getStringExtra("long")

        window.statusBarColor = Color.parseColor("#1383c3")
        getsJsonData(lat,long)
    }

    private fun getsJsonData(lat: String?, long: String?) {
        val API_KEY = "d3e65aa9c00f85cd9a14430cf6a88028"
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${API_KEY}&lang={lang}"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                setValues(response)

            },
            {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)


    }

    private fun setValues(response: JSONObject?) {

        binding.city.text = response!!.getString("name")
        val lat = response.getJSONObject("coord").getString("lat")
        val long = response.getJSONObject("coord").getString("lon")
        binding.coordinates.text = "$lat , $long"
        binding.weather.text = response.getJSONArray("weather").getJSONObject(0).getString("main")

        var temp = response.getJSONObject("main").getString("temp")

        temp = ((((temp).toFloat()-273.15)).toInt()).toString()
        binding.temp.text = "${temp}°C"
        var mintemp = response.getJSONObject("main").getString("temp_min")

        mintemp = ((((mintemp).toFloat()-273.15)).toInt()).toString()
        binding.minTemp.text = "${mintemp}°C"

        var maxtemp = response.getJSONObject("main").getString("temp_max")

        maxtemp = ((((maxtemp).toFloat()-273.15)).toInt()).toString()
        binding.maxTemp.text = "${maxtemp}°C"

        binding.pressure.text = response.getJSONObject("main").getString("pressure")+ " hPa"
        binding.humidity.text = response.getJSONObject("main").getString("humidity") + "%"

        binding.wind.text = response.getJSONObject("wind").getString("speed")

        binding.degree.text = "Degree : "+response.getJSONObject("wind").getString("deg")+"°"

       // binding.gust.text = "Gust : "+response.getJSONObject("gust").getString("wind")+"°"


    }
}