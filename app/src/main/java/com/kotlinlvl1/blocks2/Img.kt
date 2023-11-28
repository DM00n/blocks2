package com.kotlinlvl1.blocks2

import com.google.gson.annotations.SerializedName

sealed interface Result {
    data class OK(val img: Img) : Result
    data class Error(val error: String) : Result
}

data class Img(
    @SerializedName("image_url") val url: String = "",
    val tmpId: Int = 0
)

