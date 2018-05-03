package com.tipchou.sunshineboxiii.ui.test

import android.arch.lifecycle.LiveData
import com.tipchou.sunshineboxiii.entity.local.TestLocal
import com.tipchou.sunshineboxiii.entity.web.TestWeb
import com.tipchou.sunshineboxiii.support.DaggerMagicBox
import com.tipchou.sunshineboxiii.support.GeneralDataRequest
import com.tipchou.sunshineboxiii.support.GeneralSaveCallResult
import com.tipchou.sunshineboxiii.support.Resource
import com.tipchou.sunshineboxiii.support.dao.DbDao
import com.tipchou.sunshineboxiii.support.dao.WebDao
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */
@Singleton
class TestRepository @Inject constructor() {

    @Inject
    lateinit var webDao: WebDao

    @Inject
    lateinit var dbDao: DbDao

    init {
        DaggerMagicBox.create().poke(this)
    }

    fun getUser(): LiveData<Resource<List<TestLocal>>> = GeneralDataRequest<TestLocal, TestWeb>(
            loadFromDb = {
                dbDao.getTest()
            },
            shouldFetch = { true },
            createCall = { webDao.getTest() },
            saveCallResult = {
                GeneralSaveCallResult(
                        netData = it,
                        requestDb = { dbDao.getTest() },
                        deleteDb = { dbDao.removeTest(it) },
                        buildSavedList = {
                            val databaseList = ArrayList<TestLocal>()
                            for (item in it) {
                                val dbData = TestLocal(userId = item.userId, userName = item.userName)
                                databaseList.add(dbData)
                            }
                            databaseList
                        },
                        addDb = {
                            dbDao.saveTest(it)
                        }
                )
            }
    ).getAsLiveData()

}
