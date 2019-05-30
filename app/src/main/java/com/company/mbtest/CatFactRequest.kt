package com.company.mbtest

import retrofit2.Call
import retrofit2.http.GET

interface CatFactRequest {
    @GET("bins/c9uvb")
    public fun getCatFacts() : Call<List<CatFactModel>>
}

interface CatRandomImg {
    @GET("/meow")
    fun getRandomImgData() : Call<RandomImage>
}