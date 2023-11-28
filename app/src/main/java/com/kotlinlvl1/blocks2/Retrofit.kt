package com.kotlinlvl1.blocks2

import android.content.Context
import android.content.res.Resources
import android.util.Log
import io.paperdb.Paper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitController(url: String, ctx: Context) {
    init {
        Paper.init(ctx)

    }
    private val rf = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()

    private val imgApi = rf.create(ImgApi::class.java)

    suspend fun getImg(pageSize: Int, page: Int): MutableList<Result> {
        // первый запрос: пагесайз 6, паге 1
        /*
        * считтываем последний индекс из кеша.
        * сравниваем длину листа с пагесайз*паге.
        * если кеш больше или равен, не качаем из интернета
        * если кеш меньше, качаем из интернета и записываем в кеш
        *
        *
        *
        * */
        val key = "page($pageSize,$page)"
        val cached = Paper.book().read(key, mutableListOf<Result>())!!
        Log.d("TAG", "${cached.size}")
        if (cached.isNotEmpty()) {
            return cached
        }

        val response = imgApi.img(pageSize, page)
        if (!response.isSuccessful) {
            return mutableListOf(Result.Error(response.code().toString()))
        }

        val result: MutableList<Result> = response.body()!!
            .map { Result.OK(it) }
            .toMutableList()

        Paper.book().write(key, result)

        return result
    }
}