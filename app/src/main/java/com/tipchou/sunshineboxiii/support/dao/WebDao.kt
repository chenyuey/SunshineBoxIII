package com.tipchou.sunshineboxiii.support.dao

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.avos.avoscloud.*
import com.tipchou.sunshineboxiii.entity.web.RoleWeb
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
    /**
     * 测试
     */
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

    /**
     * 获取当前登录用户的用户角色列表
     * 请务必在登录状态下调用，否则将会抛出异常
     */
    fun getRole(): LiveData<ApiResponse<List<RoleWeb>>> {
        val data = MutableLiveData<ApiResponse<List<RoleWeb>>>()
        val currentUser = AVUser.getCurrentUser()
        if (currentUser == null) {
            //当前用户未登录
            throw Exception("getRole(): 当前用户未登录！")
        } else {
            currentUser.getRolesInBackground(object : AVCallback<List<AVRole>>() {
                override fun internalDone0(response: List<AVRole>?, exception: AVException?) {
                    val result = ArrayList<RoleWeb>()
                    if (response != null) {
                        for (item in response) {
                            result.add(RoleWeb(item))
                        }
                    }
                    data.value = ApiResponse(result, exception)
                }
            })
        }
        return data
    }
}
