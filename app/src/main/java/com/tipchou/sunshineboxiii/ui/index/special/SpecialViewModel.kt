package com.tipchou.sunshineboxiii.ui.index.special

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tipchou.sunshineboxiii.entity.local.SpecialSubjectLocal
import com.tipchou.sunshineboxiii.support.DaggerMagicBox
import com.tipchou.sunshineboxiii.support.GeneralObserver
import com.tipchou.sunshineboxiii.support.Repository
import com.tipchou.sunshineboxiii.support.Resource
import javax.inject.Inject

/**
 * Created by 邵励治 on 2018/5/29.
 * Perfect Code
 */
class SpecialViewModel : ViewModel() {
    @Inject
    lateinit var repository: Repository

    private val specialSubject: MutableLiveData<Resource<List<SpecialSubjectLocal>>> = MutableLiveData()
    private val specialSubjectObserver: GeneralObserver<Resource<List<SpecialSubjectLocal>>>

    init {
        DaggerMagicBox.create().poke(this)
        specialSubjectObserver = GeneralObserver(specialSubject) {
            repository.getSpecialSubject()
        }
    }

    fun getSpecialSubject(): LiveData<Resource<List<SpecialSubjectLocal>>> {
        loadSpecialSubject()
        return specialSubject
    }

    fun loadSpecialSubject() {
        specialSubjectObserver.load()
    }
}
