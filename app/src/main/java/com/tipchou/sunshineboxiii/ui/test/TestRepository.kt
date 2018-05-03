package com.tipchou.sunshineboxiii.ui.test

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.util.Log
import com.tencent.open.BrowserAuth.a
import com.tipchou.sunshineboxiii.entity.local.TestLocal
import com.tipchou.sunshineboxiii.entity.local.TestLocal_.userId
import com.tipchou.sunshineboxiii.entity.local.TestLocal_.userName
import com.tipchou.sunshineboxiii.entity.web.TestWeb
import com.tipchou.sunshineboxiii.support.*
import com.tipchou.sunshineboxiii.support.dao.DbDao
import com.tipchou.sunshineboxiii.support.dao.WebDao
import io.objectbox.android.ObjectBoxLiveData
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

    fun getUser(): LiveData<Resource<List<TestLocal>>> = GeneralDataRequest<List<TestLocal>, List<TestWeb>>(
            loadFromDb = {
                val dbSource: MediatorLiveData<List<TestLocal>> = MediatorLiveData()
                dbSource.addSource(dbDao.getTest()) {
                    dbSource.value = it
                }
                dbSource
            },
            shouldFetch = { true },
            createCall = { webDao.getTest() },
            saveCallResult = {
                if (it != null) {
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
                } else {
                    //should not be here!!!
                }
            }
    ).getAsLiveData()

}
