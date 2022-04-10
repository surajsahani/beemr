package com.martial.beemr.models

import com.google.gson.annotations.SerializedName
import com.martial.beemr.utils.Constants

data class ScoreCardModel(
    @SerializedName("error") val error : Boolean,
    @SerializedName("totalCount") val totalCount : Int,
    @SerializedName("count") val count : Int,
    @SerializedName("page") val page : Int,
    @SerializedName("limit") val limit : Int,
    @SerializedName("testScores") val testScores : ArrayList<TestScores>
){
    data class TestScores(
        @SerializedName("scores") val score : Score?,
        @SerializedName("_id") val _id : String,
        @SerializedName("email") val email : String,
        @SerializedName("exam") val exam : String,
        @SerializedName("testSeries") val testSeries : String,
        @SerializedName("testName") val testName : String,
        @SerializedName("testDate") val testDate : String,
        @SerializedName("createdAt") val createdAt : String,
        @SerializedName("updatedAt") val updatedAt : String,
        @SerializedName("__v") val __v : Int
    ){
        data class Score(
            @SerializedName("Physics") val Physics : Int?,
            @SerializedName("Chemistry") val Chemistry : Int?,
            @SerializedName("Mathematics") val Mathematics : Int?
        )

        var testLocalDate : String? = ""
            get() {
                val date = Constants.DATE_FORMAT_PARSE.parse(testDate)
                return Constants.DATE_FORMAT_LOCAL.format(date)
            }
    }
}