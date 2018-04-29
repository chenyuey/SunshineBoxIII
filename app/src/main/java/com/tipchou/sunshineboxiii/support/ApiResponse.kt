package com.tipchou.sunshineboxiii.support

import com.avos.avoscloud.AVException

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */
class ApiResponse<T>(private val data: T?, private val exception: AVException?) {
    fun isSuccessful(): Boolean = exception == null
    fun getErrorMessage(): String? = exception?.message
    fun getBody(): T? = data
}
