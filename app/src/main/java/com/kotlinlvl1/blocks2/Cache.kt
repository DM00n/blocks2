package com.kotlinlvl1.blocks2

object Cache {
    private var cache: MutableList<Img> = mutableListOf()

    fun getCache() = cache

    fun fillCache(image: Img) = cache.add(image)
}