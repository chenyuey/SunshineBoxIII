package com.tipchou.sunshineboxiii.ui.test

import android.arch.lifecycle.*
import android.support.v7.widget.RecyclerView
import com.tipchou.sunshineboxiii.entity.local.TestLocal
import com.tipchou.sunshineboxiii.support.DaggerMagicBox
import com.tipchou.sunshineboxiii.support.GeneralObserver
import com.tipchou.sunshineboxiii.support.Resource
import javax.inject.Inject

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */

class TestViewModel : ViewModel() {
    @Inject
    lateinit var repository: TestRepository
    private val user: MutableLiveData<Resource<TestLocal>> = MutableLiveData()

    private val myObserver: GeneralObserver<Resource<TestLocal>>

    init {
        DaggerMagicBox.create().poke(this)
        myObserver = GeneralObserver(user) { repository.getFirstUser() }
    }


    fun getUser(): LiveData<Resource<TestLocal>> {
        loadUser()
        return user
    }

    fun loadUser() {
        myObserver.load()
    }

}