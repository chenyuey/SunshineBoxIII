package com.tipchou.sunshineboxiii.support.dao

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVQuery
import com.avos.avoscloud.FindCallback
import com.tipchou.sunshineboxiii.pojo.webpojo.UsersWebPOJO
import com.tipchou.sunshineboxiii.support.ApiResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */

@Singleton
class WebDao @Inject constructor() {
    fun getFirstUser(): LiveData<ApiResponse<UsersWebPOJO>> {
        val data = MutableLiveData<ApiResponse<UsersWebPOJO>>()
        val query = AVQuery<AVObject>("Users")
        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(response: MutableList<AVObject>?, exception: AVException?) {
                val usersWebPOJO = UsersWebPOJO(response?.get(0))
                data.value = ApiResponse(usersWebPOJO, exception)
            }
        })
        return data
    }
}
