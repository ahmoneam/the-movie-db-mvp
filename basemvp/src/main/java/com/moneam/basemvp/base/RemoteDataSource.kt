package com.moneam.basemvp.base

import com.moneam.basemvp.BuildConfig
import com.moneam.basemvp.model.ActorsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class RemoteDataSource {
    companion object {
        val Instance = RemoteDataSource()

        private const val API_KEY = "3e68c56cf7097768305e38273efd342c"
        private const val PARAM_API_KEY = "api_key"
        private const val PARAM_PAGE = "page"
        private const val POPULAR_PEOPLE_URL = "person/popular"
        private const val TMDB_BAse_URL = "https://api.themoviedb.org/3/"
        private const val PROFILE_IMAGE_URL = "https://image.tmdb.org/t/p/w300"
    }

    private val loggingInterceptor: HttpLoggingInterceptor
        get() {
            val log = HttpLoggingInterceptor()
            log.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            return log
        }

    private val okHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val request = chain.request()

                val url = request.url

                val newUrl = url.newBuilder()
                    .addQueryParameter(PARAM_API_KEY, API_KEY).build()

                val builder = request.newBuilder()
                builder.url(newUrl)

                chain.proceed(builder.build())
            }
            .addInterceptor(loggingInterceptor)
            .callTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

    private val retrofit =
        Retrofit
            .Builder()
            .baseUrl(TMDB_BAse_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    val api = retrofit.create(Api::class.java)

    interface Api {
        @GET(POPULAR_PEOPLE_URL)
        fun getPopularPeople(@Query(PARAM_PAGE) page: Int)
                : Call<ActorsResponse>
    }
}