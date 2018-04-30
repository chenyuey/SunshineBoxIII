package com.tipchou.sunshineboxiii.support.dao

import com.tipchou.sunshineboxiii.entity.local.TestLocal
import com.tipchou.sunshineboxiii.entity.local.TestLocal_
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

    fun getFirstUser(): ObjectBoxLiveData<TestLocal> {
        val boxStore = App.getBoxStore()
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox: Box<TestLocal> = boxStore.boxFor(TestLocal::class.java)
            return ObjectBoxLiveData(userBox.query().order(TestLocal_.userId).build())
        }
    }

    fun saveUser(testLocal: TestLocal) {
        val boxStore = App.getBoxStore()
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox = boxStore.boxFor(TestLocal::class.java)
            testLocal.id = getObjectIdByUserId(testLocal.userId)
            userBox.put(testLocal)
        }
    }

    private fun getObjectIdByUserId(userId: String): Long {
        val boxStore = App.getBoxStore()
        return if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox = boxStore.boxFor(TestLocal::class.java)
            val testLocal: TestLocal? = userBox.query().equal(TestLocal_.userId, userId).build().findUnique()
            testLocal?.id ?: 0
        }
    }
}
