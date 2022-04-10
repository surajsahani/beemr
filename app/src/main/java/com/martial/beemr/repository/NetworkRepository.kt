package com.martial.beemr.repository

import com.martial.beemr.api.NetworkService
import com.martial.beemr.api.SafeApiRequest
import com.martial.beemr.models.AddScoreModel
import com.martial.beemr.models.PatchScoreModel
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val networkApi : NetworkService
) : SafeApiRequest() {

    suspend fun fetchScoreCard(email : String, page : String, limit : String) = apiRequest {
        networkApi.fetchScoreCards(email,page,limit)
    }

    suspend fun addScoreCard(scoreCard : AddScoreModel) = apiRequest {
        networkApi.addScore(scoreCard)
    }

    suspend fun updateScoreCard(scoreId : String,scoreCard : PatchScoreModel) = apiRequest {
        networkApi.updateScore(scoreId,scoreCard)
    }

    suspend fun deleteScoreCard(scoreCardId : String) = apiRequest {
        networkApi.deleteScore(scoreCardId)
    }

    suspend fun fetchTestSeries() = apiRequest {
        networkApi.fetchTestSeries()
    }
}