package com.tipchou.sunshineboxiii.ui.index

import android.arch.lifecycle.LiveData
import com.tipchou.sunshineboxiii.entity.local.RoleLocal
import com.tipchou.sunshineboxiii.entity.web.RoleWeb
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
}
