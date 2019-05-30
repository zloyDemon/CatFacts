package com.company.mbtest

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ListFacts(var facts : List<CatFactModel>) : Serializable

data class CatFactModel(@SerializedName("id") val id : Int,
                        @SerializedName("text") val text : String) : Serializable

data class RandomImage(@SerializedName("file") val imgUrl : String) : Serializable