package com.martial.beemr.models

import com.google.gson.annotations.SerializedName

data class TestSeriesModel(
    @SerializedName("error") val error : Boolean,
    @SerializedName("testSeries") val testSeries : ArrayList<String>
)