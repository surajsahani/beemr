package com.martial.beemr.models

import com.google.gson.annotations.SerializedName

data class AddScoreModel(
    @SerializedName("email") val email : String,
    @SerializedName("testSeries") val  testSeries : String,
    @SerializedName("testName") val testName : String,
    @SerializedName("testDate") val testDate : String,
    //@SerializedName("exam") val exam : String,
    @SerializedName("scores") val scores : Score
){
    data class Score(
        @SerializedName("Physics") val Physics : Int?,
        @SerializedName("Chemistry") val Chemistry : Int?,
        @SerializedName("Mathematics") val Mathematics : Int?
        )
}