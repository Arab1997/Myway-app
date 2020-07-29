package com.snatap.myway.network.models

class BaseResponse<T> {
    var success: Boolean = false
    var data: T? = null
}
