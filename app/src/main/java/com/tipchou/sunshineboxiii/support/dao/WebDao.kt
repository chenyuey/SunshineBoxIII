package com.tipchou.sunshineboxiii.support.dao


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.avos.avoscloud.*
import com.tipchou.sunshineboxiii.entity.web.*
import com.tipchou.sunshineboxiii.support.ApiResponse
import com.tipchou.sunshineboxiii.support.LessonType
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

    /**
     * 获取Lesson表中的数据
     */
    fun getLesson(lessonType: LessonType): LiveData<ApiResponse<List<LessonWeb>>> {
        val data = MutableLiveData<ApiResponse<List<LessonWeb>>>()
        val query = AVQuery<AVObject>("Lesson")
        when (lessonType) {
            LessonType.NURSERY -> query.whereEqualTo("subject", AVObject.createWithoutData("Subject", "5a701c8c1b69e6003c534903"))
            LessonType.MUSIC -> query.whereEqualTo("subject", AVObject.createWithoutData("Subject", "5a741bcb2f301e003be904ed"))
            LessonType.READING -> query.whereEqualTo("subject", AVObject.createWithoutData("Subject", "5a701c82d50eee00444134b2"))
            LessonType.GAME -> query.whereEqualTo("subject", AVObject.createWithoutData("Subject", "5a8e908dac502e0032b6225d"))
            LessonType.HEALTH -> query.whereStartsWith("tags", "domain.健康")
            LessonType.LANGUAGE -> query.whereStartsWith("tags", "domain.语言")
            LessonType.SOCIAL -> query.whereStartsWith("tags", "domain.社会")
            LessonType.SCIENCE -> query.whereStartsWith("tags", "domain.科学")
            LessonType.ART -> query.whereStartsWith("tags", "domain.艺术")
        }
        query.include("package")
        query.include("subject")
        query.include("staging_package")
        query.limit = 1000
        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(response: MutableList<AVObject>?, exception: AVException?) {
                val result = ArrayList<LessonWeb>()
                if (response != null) {
                    for (item in response) {
                        result.add(LessonWeb(item))
                    }
                }
                data.value = ApiResponse(result, exception)
            }
        })
        return data
    }

    /**
     * 获取Favorite表中的数据
     */
    fun getFavorite(): LiveData<ApiResponse<List<FavoriteWeb>>> {
        val data = MutableLiveData<ApiResponse<List<FavoriteWeb>>>()
        val currentUser = AVUser.getCurrentUser()
        if (currentUser == null) {
            //当前用户未登录
            throw Exception("getFavorite(): 当前用户未登录！")
        } else {
            val query = AVQuery<AVObject>("Favourite")
            query.whereEqualTo("user", currentUser)
            query.limit = 1000
            query.findInBackground(object : FindCallback<AVObject>() {
                override fun done(response: MutableList<AVObject>?, exception: AVException?) {
                    val result = ArrayList<FavoriteWeb>()
                    if (response != null) {
                        for (item in response) {
                            result.add(FavoriteWeb(item))
                        }
                    }
                    data.value = ApiResponse(result, exception)
                }
            })
        }
        return data
    }

    /**
     * 根据LessonId查询Lesson表中的数据
     */
    fun getLesson(lessonObjectIdList: List<String>): LiveData<ApiResponse<List<LessonWeb>>> {
        val data = MutableLiveData<ApiResponse<List<LessonWeb>>>()
        if (lessonObjectIdList.isEmpty()) {
            return data
        }
        val queryList = arrayListOf<AVQuery<AVObject>>()
        for (lessonObjectId in lessonObjectIdList) {
            val query = AVQuery<AVObject>("Lesson")
            query.whereEqualTo("objectId", lessonObjectId)
            queryList.add(query)
        }
        val query: AVQuery<AVObject> = AVQuery.or(queryList)
        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(response: MutableList<AVObject>?, exception: AVException?) {
                val result = ArrayList<LessonWeb>()
                if (response != null) {
                    for (item in response) {
                        result.add(LessonWeb(item))
                    }
                }
                data.value = ApiResponse(result, exception)
            }
        })
        return data
    }


    /**
     * 获取SpecialSubject表中的数据
     */
    fun getSpecialSubject(): LiveData<ApiResponse<List<SpecialSubjectWeb>>> {
        val data = MutableLiveData<ApiResponse<List<SpecialSubjectWeb>>>()
        val query = AVQuery<AVObject>("SpecialSubject")
        query.include("picture")
        query.limit = 1000
        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(response: MutableList<AVObject>?, exception: AVException?) {
                val result = arrayListOf<SpecialSubjectWeb>()
                if (response != null) {
                    for (item in response) {
                        result.add(SpecialSubjectWeb(item))
                    }
                }
                data.value = ApiResponse(result, exception)
            }
        })
        return data
    }

    fun getLessonSubject(specialObjectId: String): LiveData<ApiResponse<List<LessonSubjectWeb>>> {
        val data = MutableLiveData<ApiResponse<List<LessonSubjectWeb>>>()
        val query = AVQuery<AVObject>("LessonSpecial")
        query.include("special")
        query.include("lesson")
        query.limit = 1000
        query.whereEqualTo("special", AVObject.createWithoutData("SpecialSubject", specialObjectId))
        query.findInBackground(object : FindCallback<AVObject>() {
            override fun done(response: MutableList<AVObject>?, exception: AVException?) {
                val result = arrayListOf<LessonSubjectWeb>()
                if (response != null) {
                    for (item in response) {
                        result.add(LessonSubjectWeb(item))
                    }
                }
                data.value = ApiResponse(result, exception)
            }
        })
        return data
    }
}
