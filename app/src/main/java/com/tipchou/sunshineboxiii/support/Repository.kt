package com.tipchou.sunshineboxiii.support

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.tipchou.sunshineboxiii.entity.local.DownloadLocal
import com.tipchou.sunshineboxiii.entity.local.FavoriteLocal
import com.tipchou.sunshineboxiii.entity.local.LessonLocal
import com.tipchou.sunshineboxiii.entity.local.RoleLocal
import com.tipchou.sunshineboxiii.entity.web.FavoriteWeb
import com.tipchou.sunshineboxiii.entity.web.LessonWeb
import com.tipchou.sunshineboxiii.entity.web.RoleWeb
import com.tipchou.sunshineboxiii.support.dao.DbDao
import com.tipchou.sunshineboxiii.support.dao.WebDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by 邵励治 on 2018/4/29.
 * Perfect Code
 */
@Singleton
class Repository @Inject constructor() {
    @Inject
    lateinit var webDao: WebDao

    @Inject
    lateinit var dbDao: DbDao

    init {
        DaggerMagicBox.create().poke(this)
    }

    fun getUserRole(): LiveData<Resource<List<RoleLocal>>> {
        return GeneralDataRequest<RoleLocal, RoleWeb>(
                loadFromDb = { dbDao.getRole() },
                shouldFetch = { true },
                createCall = { webDao.getRole() },
                deleteDb = { dbDao.removeRole(it) },
                buildSavedList = {
                    val databaseList = ArrayList<RoleLocal>()
                    for (item in it) {
                        val dbData = RoleLocal(name = item.name)
                        databaseList.add(dbData)
                    }
                    databaseList
                },
                saveCallResult = { dbDao.saveRole(it) }
        ).getAsLiveData()
    }

    fun getLesson(lessonType: LessonType): LiveData<Resource<List<LessonLocal>>> {
        return GeneralDataRequest<LessonLocal, LessonWeb>(
                loadFromDb = { dbDao.getLesson(lessonType) },
                shouldFetch = { true },
                createCall = { webDao.getLesson(lessonType) },
                deleteDb = { dbDao.removeLesson(it) },
                buildSavedList = {
                    val databaseList = ArrayList<LessonLocal>()
                    for (item in it) {
                        val dbData = LessonLocal(
                                objectId = item.objectId,
                                isPublish = item.isPublish,
                                tags = item.tags,
                                packageUrl = item.packageUrl,
                                name = item.name,
                                versionCode = item.versionCode,
                                draftVersionCode = item.draftVersionCode,
                                subject = item.subject,
                                compiler = item.compiler,
                                stagingPackageUrl = item.stagingPackageUrl,
                                areChecked = item.isChecked)
                        databaseList.add(dbData)
                    }
                    databaseList
                },
                saveCallResult = { dbDao.saveLesson(it) }
        ).getAsLiveData()
    }

    fun getFavorite(): LiveData<Resource<List<FavoriteLocal>>> {
        return GeneralDataRequest<FavoriteLocal, FavoriteWeb>(
                loadFromDb = { dbDao.getFavorite() },
                shouldFetch = { true },
                createCall = { webDao.getFavorite() },
                deleteDb = { dbDao.removeFavorite(it) },
                buildSavedList = {
                    val databaseList = ArrayList<FavoriteLocal>()
                    for (item in it) {
                        val dbData = FavoriteLocal(action = item.action, lessonId = item.lessonId)
                        databaseList.add(dbData)
                    }
                    databaseList
                },
                saveCallResult = { dbDao.saveFavorite(it) }
        ).getAsLiveData()
    }

    fun getDownload(): LiveData<List<DownloadLocal>> {
        return dbDao.getDownload()
    }

    fun getLesson(objectIdList: List<String>): LiveData<Resource<List<LessonLocal>>> =
            GeneralDataRequest(
                    loadFromDb = {
                        val objectIdListLiveData = MutableLiveData<List<LessonLocal>>()
                        objectIdListLiveData.value = dbDao.getLesson(objectIdList)
                        objectIdListLiveData
                    },
                    shouldFetch = { true },
                    createCall = { webDao.getLesson(objectIdList) },
                    deleteDb = { dbDao.removeLesson(it) },
                    buildSavedList = {
                        val databaseList = ArrayList<LessonLocal>()
                        for (item in it) {
                            val dbData = LessonLocal(
                                    objectId = item.objectId,
                                    isPublish = item.isPublish,
                                    tags = item.tags,
                                    packageUrl = item.packageUrl,
                                    name = item.name,
                                    versionCode = item.versionCode,
                                    draftVersionCode = item.draftVersionCode,
                                    subject = item.subject,
                                    compiler = item.compiler,
                                    stagingPackageUrl = item.stagingPackageUrl,
                                    areChecked = item.isChecked)
                            databaseList.add(dbData)
                        }
                        databaseList
                    },
                    saveCallResult = { dbDao.saveLesson(it) }
            ).getAsLiveData()
}