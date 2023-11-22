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
        println("response.toString()")
        val response = imgApi.img()

        println(response.toString())
//        val res: MutableList<Result> = emptyList<Result>().toMutableList()
//        if (response.isSuccessful) {
//            for (img: Img in response.body()!!) {
//                res.add(Result.OK(img))
//            }
//        } else {
//            res.add(Result.Error(response.code().toString()))
//        }
        return if (response.isSuccessful) {
            println(response.toString())
            response.body()?.let{
                Result.OK(it)
            } ?: Result.Error("No image")
        } else {
            println(response.toString())
            Result.Error(response.code().toString())
        }
    }
//        return res
//    }
}