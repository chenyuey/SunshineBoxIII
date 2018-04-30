package com.tipchou.sunshineboxiii.ui.main

import android.arch.lifecycle.*
import android.support.annotation.MainThread
import com.bumptech.glide.Glide.init
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

    var myObserver: MyObserver? = null

    init {
        DaggerMagicBox.create().poke(this)
    }

    private val user: MutableLiveData<Resource<UsersLocalPOJO>> = MediatorLiveData()

    fun getUser(): LiveData<Resource<UsersLocalPOJO>> {
        if (myObserver == null) {
            myObserver = MyObserver(user, repository)
        }
        loadUser()
        return user
    }

    fun loadUser() {
        myObserver?.load()
    }

    class MyObserver constructor(private val user: MutableLiveData<Resource<UsersLocalPOJO>>, private val repository: MainRepository) : Observer<Resource<UsersLocalPOJO>> {

        var request: LiveData<Resource<UsersLocalPOJO>>? = null

        fun load() {
            unregister()
            request = repository.getFirstUser()
            request?.observeForever(this)
        }

        private fun unregister() {
            if (request != null) {
                request?.removeObserver(this)
                request = null
            }
        }

        override fun onChanged(t: Resource<UsersLocalPOJO>?) {
            user.value = t
        }

    }

}