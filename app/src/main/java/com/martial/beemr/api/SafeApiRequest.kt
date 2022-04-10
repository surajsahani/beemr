package com.martial.beemr.api

import android.util.Log
import retrofit2.Response

abstract class SafeApiRequest{
    suspend fun <T : Any> apiRequest(call: suspend() -> Response<T>) : T?{
        val response = call.invoke()
        if(response.isSuccessful){
            return response.body()
        }else{
            Log.d("responseLog",response.toString())
            throw Exception(response.code().toString())
        }
    }
}