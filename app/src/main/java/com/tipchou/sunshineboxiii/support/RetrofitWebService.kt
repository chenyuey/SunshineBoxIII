package com.tipchou.sunshineboxiii.support

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by 邵励治 on 2018/5/14.
 * Perfect Code
 */
interface RetrofitWebService {
    @Streaming
    @GET
    fun downloadFile(@Url url: String): Call<ResponseBody>
}
