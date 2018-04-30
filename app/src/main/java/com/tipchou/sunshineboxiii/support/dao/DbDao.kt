package com.tipchou.sunshineboxiii.support.dao

import com.tipchou.sunshineboxiii.entity.local.UsersLocal
import com.tipchou.sunshineboxiii.entity.local.UsersLocal_
import com.tipchou.sunshineboxiii.support.App
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */
@Singleton
class DbDao @Inject constructor() {

    fun getFirstUser(): ObjectBoxLiveData<UsersLocal> {
        val boxStore = App.getBoxStore()
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox: Box<UsersLocal> = boxStore.boxFor(UsersLocal::class.java)
            return ObjectBoxLiveData(userBox.query().order(UsersLocal_.userId).build())
        }
    }

    fun saveUser(usersLocal: UsersLocal) {
        val boxStore = App.getBoxStore()
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox = boxStore.boxFor(UsersLocal::class.java)
            usersLocal.id = getObjectIdByUserId(usersLocal.userId)
            userBox.put(usersLocal)
        }
    }

    private fun getObjectIdByUserId(userId: String): Long {
        val boxStore = App.getBoxStore()
        return if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox = boxStore.boxFor(UsersLocal::class.java)
            val usersLocal: UsersLocal? = userBox.query().equal(UsersLocal_.userId, userId).build().findUnique()
            usersLocal?.id ?: 0
        }
    }
}
