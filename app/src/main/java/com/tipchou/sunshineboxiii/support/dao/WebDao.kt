package com.tipchou.sunshineboxiii.support.dao

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVObject
import com.avos.avoscloud.AVQuery
import com.avos.avoscloud.FindCallback
import com.tipchou.sunshineboxiii.entity.web.TestWeb
import com.tipchou.sunshineboxiii.support.ApiResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */

@Singleton
class WebDao @Inject constructor() {
    fun getTest(): LiveData<ApiResponse<List<TestWeb>>> {
        val data = MutableLiveData<ApiResponse<List<TestWeb>>>()
        val query = AVQuery<AVObject>("Users")
        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(response: MutableList<AVObject>?, exception: AVException?) {
                val result = ArrayList<TestWeb>()
                if (response != null) {
                    for (item in response) {
                        result.add(TestWeb(item))
                    }
                }
                data.value = ApiResponse(result, exception)
            }
        })
        return data
    }
}
