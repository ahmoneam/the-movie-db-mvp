package com.moneam.themoviedb

import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.moneam.basemvp.model.Actor
import com.moneam.basemvp.model.ActorsResponse
import com.moneam.basemvp.model.Image
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.io.BufferedInputStream
import java.io.IOException

class MainModel {
    private val gson = Gson()

    fun getActors(page: Int, onSuccess: (list: List<Actor>) -> Unit) {
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
                        val actorsResponse = gson.fromJson<ActorsResponse>(
                            response.body?.string(), ActorsResponse::class.java
                        )

                        Handler(Looper.getMainLooper()).post {
                            onSuccess(actorsResponse.results)
                        }
                    }
                }
            })
    }

    fun loadImage(image: Image, onSuccess: (image: Image) -> Unit) {
        val urlBuilder = PROFILE_IMAGE_URL
            .toHttpUrlOrNull()?.newBuilder()

        urlBuilder?.addPathSegment(image.url)
        val url = urlBuilder?.build().toString()

        val httpClient = OkHttpClient()

        val request = Request.Builder().url(url).build()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("MainModel", "error", e)
            }

            override fun onResponse(call: Call, response: Response) {

                val inputStream = response
                    .body?.byteStream()
                Log.i("inputStream", "inputstream value = $inputStream")
                // convert inputstram to bufferinoutstream

                if (inputStream != null) {
                    val bufferedInputStream = BufferedInputStream(inputStream)
                    val bitmap = BitmapFactory.decodeStream(bufferedInputStream)
                    image.bitmap = bitmap
                    Handler(Looper.getMainLooper()).post {
                        onSuccess(image)
                    }
                } else {
                    Log.e("inputStream", "inputstream error to bitmap")
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