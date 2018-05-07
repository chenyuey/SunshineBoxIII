package com.tipchou.sunshineboxiii.support.dao

import android.os.LocaleList
import com.tipchou.sunshineboxiii.entity.local.RoleLocal
import com.tipchou.sunshineboxiii.entity.local.RoleLocal_
import com.tipchou.sunshineboxiii.entity.local.TestLocal
import com.tipchou.sunshineboxiii.entity.local.TestLocal_
import com.tipchou.sunshineboxiii.support.App
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.android.ObjectBoxLiveData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */
@Singleton
class DbDao @Inject constructor() {

    private val boxStore: BoxStore? by lazy { App.getBoxStore() }

    fun getTest(): ObjectBoxLiveData<TestLocal> {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox: Box<TestLocal> = boxStore!!.boxFor(TestLocal::class.java)
            return ObjectBoxLiveData(userBox.query().order(TestLocal_.userId).build())
        }
    }

    fun saveTest(testLocalList: List<TestLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox = boxStore!!.boxFor(TestLocal::class.java)
            userBox.put(testLocalList)
        }
    }

    fun removeTest(testLocalList: List<TestLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val userBox = boxStore!!.boxFor(TestLocal::class.java)
            userBox.remove(testLocalList)
        }
    }

    fun getRole(): ObjectBoxLiveData<RoleLocal> {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val roleBox: Box<RoleLocal> = boxStore!!.boxFor(RoleLocal::class.java)
            return ObjectBoxLiveData(roleBox.query().order(RoleLocal_.id).build())
        }
    }

    fun saveRole(roleLocaleList: List<RoleLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val roleBox = boxStore!!.boxFor(RoleLocal::class.java)
            roleBox.put(roleLocaleList)
        }
    }

    fun removeRole(roleLocaleList: List<RoleLocal>) {
        if (boxStore == null) {
            //should not be here!!!!!!
            throw Exception("App.getBoxStore() get null!!!")
        } else {
            val roleBox = boxStore!!.boxFor(RoleLocal::class.java)
            roleBox.remove(roleLocaleList)
        }
    }
}
