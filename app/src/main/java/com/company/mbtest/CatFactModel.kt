package com.company.mbtest

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ListFacts(@SerializedName("all") var facts : List<CatFactModel>)

data class CatFactModel(@SerializedName("_id") val id : String,
                        @SerializedName("text") val text : String) : Serializable