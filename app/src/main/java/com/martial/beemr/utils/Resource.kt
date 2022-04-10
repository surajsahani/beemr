package com.martial.beemr.utils

class Resource <out T>(
    val status : Status,
    val data : T?,
    val msg : String?
){

    companion object{
        fun <T> success(data: T) : Resource<T>
                = Resource(status = Status.SUCCESS,data = data,msg = null)

        fun <T> error(message: String,data : T? = null) : Resource<T>
                = Resource(status = Status.ERROR,msg = message,data = data)

        fun <T> loading(data: T? = null) : Resource<T>
                = Resource(status = Status.LOADING,data = data,msg = null)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}