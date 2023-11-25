package com.kotlinlvl1.blocks2

import android.util.Log
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET


class RetrofitController(url: String) {
    private val rf = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()

    private val imgApi = rf.create(ImgApi::class.java)

    suspend fun getImg(): Result {
        val response = imgApi.img()
        return if (response.isSuccessful) {
            response.body()?.let{
                Result.OK(it[0])
            } ?: Result.Error("No image")
        } else {
            Result.Error(response.code().toString())
        }
    }
}