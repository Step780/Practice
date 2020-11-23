package com.example.practice

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.example.practice.models.Weather
import com.google.gson.Gson
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val request = "https://api.openweathermap.org"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_first, container, false)
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        if (!sharedPref.contains("city")) {
            parentFragmentManager.beginTransaction().replace(R.id.fragment, SecondFragment())
                .commit()
            Toast.makeText(context, "Выберите город", Toast.LENGTH_LONG).show()
            return root
        }
        val weatherText = root.findViewById<TextView>(R.id.weather_text)
        val retrofit = Retrofit.Builder()
            .baseUrl(request)
            .addConverterFactory(GsonConverterFactory.create())
            .build().also {


                it.create(OpenWeatherMapService::class.java)
                    .getWeather("Москва", BuildConfig.API_KEY)
                    .enqueue(object : retrofit2.Callback<Weather> {


                        override fun onFailure(call: Call<Weather>, t: Throwable) {
                            Toast.makeText(
                                context,
                                "Проверьте соединение с интернетом",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        val activity = (context as Activity)
                        override fun onResponse(
                            call: retrofit2.Call<Weather>, response: retrofit2.Response<Weather>
                        ) {
                            val weather = response.body()!!
                            val absoluteZero = 273.16
                            val message =
                                "Погода в городе ${sharedPref.getString("city", "Москва")}:\n\n" +
                                        "Температура: ${(weather.main.temp - absoluteZero).roundToInt()}°C\n\n" +
                                        "Ощущается как ${(weather.main.feels_like - absoluteZero).roundToInt()}\n\n" +
                                        "Погода: ${weather.weather[0].description}\n\n" +
                                        when (weather.weather[0].main) {
                                            "Snow" -> "Можно полежать в снегу"
                                            "Broken clouds" -> "Солнце будет не всегда"
                                            "Clear" -> "Отличное время, чтобы погулять"
                                            "Clouds" -> "Лучше одетьсся потеплее"
                                            else -> "Сегодня лучше сидеть дома"
                                        }
                            activity.runOnUiThread {
                                weatherText.text = message
                            }
                        }




                    })
                weatherText.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
                return root
            }
    }
}