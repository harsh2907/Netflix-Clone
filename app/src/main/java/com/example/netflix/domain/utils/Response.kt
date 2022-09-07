package com.example.netflix.domain.utils

sealed class Response<T>(val data:T?=null,val message:String = ""){
    class Success<T>( data:T):Response<T>(data = data)
    class Loading<T>():Response<T>()
    class Error<T>( message:String):Response<T>(message = message)
}
