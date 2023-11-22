package com.kotlinlvl1.blocks2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ImgApi {
//    @Headers(
//        "q: excited",
//        "key: AIzaSyCbvScE1mgJovQMHrjyH1ZHMv0I8gjuHNE",
//        "limit: 8"
//    )
    @GET("beers/random")
    suspend fun img(): Response<Img>
}