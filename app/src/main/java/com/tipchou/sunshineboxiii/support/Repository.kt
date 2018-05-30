package com.tipchou.sunshineboxiii.support

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.tipchou.sunshineboxiii.entity.local.*
import com.tipchou.sunshineboxiii.entity.web.*
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

    fun getSpecialSubject(): LiveData<Resource<List<SpecialSubjectLocal>>> {
        return GeneralDataRequest<SpecialSubjectLocal, SpecialSubjectWeb>(
                loadFromDb = { dbDao.getSpecialSubject() },
                shouldFetch = { true },
                createCall = { webDao.getSpecialSubject() },
                deleteDb = { dbDao.removeSpecialSubject(it) },
                buildSavedList = {
                    val databaseList = ArrayList<SpecialSubjectLocal>()
                    for (item in it) {
                        val dbData = SpecialSubjectLocal(objectId = item.objectId, recommendStatus = item.recommendStatus, title = item.title, pictureUrl = item.pictureUrl, describe = item.describe, onLine = item.onLine)
                        databaseList.add(dbData)
                    }
                    databaseList
                },
                saveCallResult = { dbDao.saveSpecialSubject(it) }
        ).getAsLiveData()
    }

    fun getDownload(): LiveData<List<DownloadLocal>> {
        return dbDao.getDownload()
    }

    fun getLesson(objectIdList: List<String>): LiveData<Resource<List<LessonLocal>>> {
        return GeneralDataRequest(
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

    fun getLessonSubject(specialObjectId: String): LiveData<Resource<List<LessonSubjectLocal>>> {
        return GeneralDataRequest<LessonSubjectLocal, LessonSubjectWeb>(
                loadFromDb = { dbDao.getLessonSubject(specialObjectId) },
                shouldFetch = { true },
                createCall = { webDao.getLessonSubject(specialObjectId) },
                deleteDb = { dbDao.removeLessonSubject(it) },
                buildSavedList = {
                    val databaseList = arrayListOf<LessonSubjectLocal>()
                    for (item in it) {
                        val dbData = LessonSubjectLocal(objectId = item.objectId, specialObjectId = item.specialObjectId, lessonObjectId = item.lessonObjectId)
                        databaseList.add(dbData)
                    }
                    databaseList
                },
                saveCallResult = { dbDao.saveLessonSubject(it) }
        ).getAsLiveData()
    }

}