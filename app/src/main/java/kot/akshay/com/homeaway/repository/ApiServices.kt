package com.akki.cineholic.rest

import kot.akshay.com.homeaway.pojo.FetchJSON
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Api to hit with respect to query
 */
interface ApiServices {

    @GET("venues/search?v=20161101")
    fun search(
        @Query("client_id") clientID: String,
        @Query("client_secret") clientSecret: String,
        @Query("near") ll: String,
        @Query("query") intent: String,
        @Query("limit") limit: Int
    ): Call<FetchJSON>
}