package com.kotlinlvl1.blocks2

import android.content.res.Resources
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitController(url: String) {
    private val rf = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()

    private val imgApi = rf.create(ImgApi::class.java)

    suspend fun getImg(pageSize: Int, page: Int): List<Result> {
        val response = imgApi.img(pageSize, page)
        if (response.body().isNullOrEmpty()) return listOf()
        val resList = mutableListOf<Result>()
        for (item in response.body()!!) {
            if (response.isSuccessful) {
                response.body()?.let {
                    resList.add(Result.OK(item))
                } ?: resList.add(Result.Error(Resources.getSystem().getString(R.string.errorLoad)))
            } else {
                resList.add(Result.Error(response.code().toString()))
            }
        }
        return resList
    }
}