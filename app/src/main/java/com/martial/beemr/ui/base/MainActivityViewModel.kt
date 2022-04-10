package com.martial.beemr.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.martial.beemr.repository.NetworkRepository
import com.martial.beemr.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    fun fetchScoreCards(email : String, page : String, limit : String) = liveData(Dispatchers.IO){
        emit(Resource.loading())
        try{
            val apiResponse = networkRepository.fetchScoreCard(email, page, limit)
            emit(Resource.success(apiResponse))
        }catch(e : Exception){
            emit(Resource.error(e.localizedMessage))
        }
    }

    fun deleteScoreCards(scoreCardId : String) = liveData(Dispatchers.IO){
        emit(Resource.loading())
        try{
            val apiResponse = networkRepository.deleteScoreCard(scoreCardId)
            emit(Resource.success(apiResponse))
        }catch(e : Exception){
            emit(Resource.error(e.localizedMessage))
        }
    }
}