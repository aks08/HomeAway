package com.akki.cineholic.rest


import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

enum class RetrofitClient {
    instance;

    @Volatile
    private var sRetrofit: Retrofit? = null
    private val baseUrl = "https://api.foursquare.com/v2/"
    val apiService: ApiServices


    init {
        sRetrofit = initRetrofit(baseUrl)
        apiService = sRetrofit!!.create(ApiServices::class.java)
    }

    private fun initRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(createClient())

            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }


    private fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).build()

    }

    companion object {

        private val TAG = RetrofitClient::class.java.simpleName
    }



}