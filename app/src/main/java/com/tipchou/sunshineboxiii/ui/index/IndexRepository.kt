package com.tipchou.sunshineboxiii.ui.index

import android.arch.lifecycle.LiveData
import com.tipchou.sunshineboxiii.entity.local.UserLocal
import com.tipchou.sunshineboxiii.entity.web.UserWeb
import com.tipchou.sunshineboxiii.support.DaggerMagicBox
import com.tipchou.sunshineboxiii.support.GeneralDataRequest
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

//    fun getUserRole(): LiveData<Resource<UserLocal>> = GeneralDataRequest<UserLocal, UserWeb>(
//            loadFromDb = {},
//            shouldFetch = {},
//            createCall = {},
//            saveCallResult = {}
//    ).getAsLiveData()
}
