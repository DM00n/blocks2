package com.kotlinlvl1.blocks2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ImgApi {
//    @Headers(
//        "per_page: 10",
//    )
    @GET("beers/random")
    suspend fun img(): Response<List<Img>>
}