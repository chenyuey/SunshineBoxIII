package com.tipchou.sunshineboxiii.ui.index

import android.arch.lifecycle.LiveData
import com.tipchou.sunshineboxiii.entity.local.LessonLocal
import com.tipchou.sunshineboxiii.entity.local.RoleLocal
import com.tipchou.sunshineboxiii.entity.web.LessonWeb
import com.tipchou.sunshineboxiii.entity.web.RoleWeb
import com.tipchou.sunshineboxiii.support.DaggerMagicBox
import com.tipchou.sunshineboxiii.support.GeneralDataRequest
import com.tipchou.sunshineboxiii.support.LessonType
import com.tipchou.sunshineboxiii.support.Resource
import com.tipchou.sunshineboxiii.support.dao.DbDao
import com.tipchou.sunshineboxiii.support.dao.WebDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by 邵励治 on 2018/4/29.
 * Perfect Code
 */
@Singleton
class IndexRepository @Inject constructor() {
    @Inject
    lateinit var webDao: WebDao

    @Inject
    lateinit var dbDao: DbDao

    init {
        DaggerMagicBox.create().poke(this)
    }

    fun getUserRole(): LiveData<Resource<List<RoleLocal>>> = GeneralDataRequest<RoleLocal, RoleWeb>(
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
}
