package com.snatap.myway.network

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("auth/login")
    fun login(@Body body: LoginRequest): Single<Token>

    @POST("auth/register")
    fun register(@Body body: RegisterRequest): Single<RegisterRequest>

    @POST("auth/logout")
    fun logout(): Single<MessageResp>

    @POST("auth/forgot_password")
    fun forgotPassword(): Single<MessageResp>

}

data class ErrorResp(val message: String, val errors: Any? = null)

data class MessageResp(val message: String)

data class SuccessResp(val success: Boolean)

data class Token(
    val access_token: String,
    val token_type: String,
    val unique_id: String
)

data class RegisterRequest(
    val full_name: String,
    val email: String,
    val phone: String
)

data class LoginRequest(
    val phone: String,
    val password: String
)