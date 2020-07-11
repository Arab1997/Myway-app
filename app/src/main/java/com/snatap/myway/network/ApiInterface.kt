package com.snatap.myway.network

interface ApiInterface {

}

data class ErrorResp(val message: String, val errors: Any? = null)