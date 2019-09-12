package com.moneam.themoviedb

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var controller: MainController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_actors.layoutManager = LinearLayoutManager(this)
        rv_actors.adapter = ActorsAdapter()

        controller = MainController(this, MainModel())

        controller.onCreated()
    }
}

class MainController(val activity: MainActivity, val model: MainModel) {
    private var page: Int = 1

    fun onCreated() {
        model.getActors(page)
    }
}

class MainModel {
    fun getActors(page: Int) {
        val urlBuilder = POPULAR_PEOPLE_URL
            .toHttpUrlOrNull()?.newBuilder()

        urlBuilder?.addQueryParameter(PARAM_API_KEY, API_KEY)
        urlBuilder?.addQueryParameter(PARAM_PAGE, page.toString())
        val url = urlBuilder?.build().toString()

        val httpClient = OkHttpClient()

        val request = Request.Builder().url(url).build()

        httpClient.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e("MainModel", "error", e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.code == 200) {
                        Log.v("MainModel", response.body?.string() ?: "")
                    }
                }
            })
    }

    companion object {
        const val API_KEY = "3e68c56cf7097768305e38273efd342c"
        const val PARAM_API_KEY = "api_key"
        const val PARAM_PAGE = "page"
        const val POPULAR_PEOPLE_URL = "https://api.themoviedb.org/3/person/popular"
        const val PROFILE_IMAGE_URL = "https://image.tmdb.org/t/p/w300"
    }
}

