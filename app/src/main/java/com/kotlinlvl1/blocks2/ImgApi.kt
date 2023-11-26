package com.kotlinlvl1.blocks2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImgApi {
    @GET("beers")
    suspend fun img(
        @Query("per_page") pageSize: Int,
        @Query("page") currentPage: Int
    ): Response<List<Img>>
}