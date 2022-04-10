package com.martial.beemr.models

import com.google.gson.annotations.SerializedName
import com.martial.beemr.models.AddScoreModel

data class PatchScoreModel(
    @SerializedName("scores") val scores : AddScoreModel.Score
)