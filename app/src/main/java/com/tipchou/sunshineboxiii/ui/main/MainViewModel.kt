package com.tipchou.sunshineboxiii.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.tipchou.sunshineboxiii.pojo.localpojo.UsersLocalPOJO
import com.tipchou.sunshineboxiii.support.DaggerMagicBox
import com.tipchou.sunshineboxiii.support.Resource
import javax.inject.Inject

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */

class MainViewModel : ViewModel() {
    @Inject
    lateinit var repository: MainRepository

    init {
        DaggerMagicBox.create().poke(this)
    }

    private var user: LiveData<Resource<UsersLocalPOJO>>? = null

    fun getUser(): LiveData<Resource<UsersLocalPOJO>>? {
        user = repository.getFirstUser()
        return user
    }
}