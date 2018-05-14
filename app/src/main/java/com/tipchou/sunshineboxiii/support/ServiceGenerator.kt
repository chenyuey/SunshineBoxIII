package com.tipchou.sunshineboxiii.support

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by 邵励治 on 2018/5/14.
 * Perfect Code
 */
class ServiceGenerator {
    companion object {
        private val gson = GsonBuilder().create()
        private val builder = Retrofit.Builder().baseUrl("http://www.baidu.com/").addConverterFactory(GsonConverterFactory.create(gson))
        private val retrofit = builder.build()

        fun <S> createService(serviceClass: Class<S>): S = retrofit.create(serviceClass)
    }
}
