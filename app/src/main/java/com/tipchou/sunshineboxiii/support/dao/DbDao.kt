package com.tipchou.sunshineboxiii.support.dao

import com.tencent.qc.stat.common.User
import com.tipchou.sunshineboxiii.pojo.localpojo.UsersLocalPOJO
import com.tipchou.sunshineboxiii.pojo.localpojo.UsersLocalPOJO_
import com.tipchou.sunshineboxiii.support.App
import io.objectbox.Box
import io.objectbox.android.ObjectBoxLiveData
import io.objectbox.query.Query
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */
@Singleton
class DbDao @Inject constructor() {

    fun getFirstUser(): ObjectBoxLiveData<UsersLocalPOJO> {
        val boxStore = App.getBoxStore()
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox: Box<UsersLocalPOJO> = boxStore.boxFor(UsersLocalPOJO::class.java)
            return ObjectBoxLiveData(userBox.query().order(UsersLocalPOJO_.userId).build())
        }
    }

    fun saveUser(usersLocalPOJO: UsersLocalPOJO) {
        val boxStore = App.getBoxStore()
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox = boxStore.boxFor(UsersLocalPOJO::class.java)
            usersLocalPOJO.id = getObjectIdByUserId(usersLocalPOJO.userId)
            userBox.put(usersLocalPOJO)
        }
    }

    private fun getObjectIdByUserId(userId: String): Long {
        val boxStore = App.getBoxStore()
        return if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox = boxStore.boxFor(UsersLocalPOJO::class.java)
            val usersLocalPOJO: UsersLocalPOJO? = userBox.query().equal(UsersLocalPOJO_.userId, userId).build().findUnique()
            usersLocalPOJO?.id ?: 0
        }
    }
}
