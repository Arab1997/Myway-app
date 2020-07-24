package com.snatap.myway.network

import retrofit2.http.POST

interface ApiInterface {
    @POST("auth/register")
    fun register()
}

data class ErrorResp(val message: String, val errors: Any? = null)

data class RegisterRequest(
    val full_name: String,
    val email: String,
    val phone: String
)