package com.tipchou.sunshineboxiii.ui.main

import android.arch.lifecycle.*
import com.tipchou.sunshineboxiii.entity.local.UsersLocal
import com.tipchou.sunshineboxiii.support.DaggerMagicBox
import com.tipchou.sunshineboxiii.support.GeneralObserver
import com.tipchou.sunshineboxiii.support.Resource
import javax.inject.Inject

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */

class MainViewModel : ViewModel() {
    @Inject
    lateinit var repository: MainRepository

    var myObserver: GeneralObserver<Resource<UsersLocal>>? = null

    init {
        DaggerMagicBox.create().poke(this)
    }

    private val user: MutableLiveData<Resource<UsersLocal>> = MediatorLiveData()

    fun getUser(): LiveData<Resource<UsersLocal>> {
        if (myObserver == null) {
            myObserver = GeneralObserver(user) { repository.getFirstUser() }
        }
        loadUser()
        return user
    }

    fun loadUser() {
        myObserver?.load()
    }



}