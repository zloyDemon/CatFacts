package com.company.mbtest

import retrofit2.Call
import retrofit2.http.GET

interface CatFactRequest {
    @GET("/facts")
    public fun getCatFacts() : Call<ListFacts>
}