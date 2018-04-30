package com.tipchou.sunshineboxiii.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.tipchou.sunshineboxiii.entity.local.UsersLocal
import com.tipchou.sunshineboxiii.entity.web.UsersWeb
import com.tipchou.sunshineboxiii.support.DaggerMagicBox
import com.tipchou.sunshineboxiii.support.GeneralDataRequest
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
class MainRepository @Inject constructor() {
    @Inject
    lateinit var webDao: WebDao

    @Inject
    lateinit var dbDao: DbDao

    init {
        DaggerMagicBox.builder().build().poke(this)
    }

    fun getFirstUser(): LiveData<Resource<UsersLocal>> {
        return GeneralDataRequest<UsersLocal, UsersWeb>(
                loadFromDb = {
                    val dbSource: MediatorLiveData<UsersLocal> = MediatorLiveData()
                    dbSource.addSource(dbDao.getFirstUser()) {
                        if (it?.size == 0) {
                            dbSource.value = null
                        } else {
                            dbSource.value = it?.get(0)
                        }
                    }
                    dbSource
                },
                shouldFetch = { true },
                createCall = {
                    webDao.getFirstUser()
                },
                saveCallResult = {
                    val userId = it?.user_id
                    val userName = it?.user_name
                    if (userId == null || userName == null) {
                        //should not be here
                        throw Exception("UsersWeb's userId or userName is null!!!")
                    } else {
                        val usersLocalPOJO = UsersLocal(userId = userId, userName = userName)
                        dbDao.saveUser(usersLocalPOJO)
                    }
                }
        ).getAsLiveData()
    }
}
