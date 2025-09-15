package com.example.retrofit.core.api

 sealed class NetworkResponse<out t> {
     //val value: Any

     data class Success<out T>(val data: T) : NetworkResponse<T>()
     data class Error(val message: String) : NetworkResponse<Nothing>()
     object Loading : NetworkResponse<Nothing>()

}