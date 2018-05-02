package com.tipchou.sunshineboxiii.ui.index

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bumptech.glide.Glide.init
import com.tipchou.sunshineboxiii.entity.local.DownloadLocal
import com.tipchou.sunshineboxiii.entity.local.LessonLocal
import com.tipchou.sunshineboxiii.entity.local.UserLocal
import com.tipchou.sunshineboxiii.support.DaggerMagicBox
import com.tipchou.sunshineboxiii.support.GeneralObserver
import com.tipchou.sunshineboxiii.support.Resource
import javax.inject.Inject

/**
 * Created by 邵励治 on 2018/4/29.
 * Perfect Code
 */
class IndexViewModel : ViewModel() {
    @Inject
    lateinit var repository: IndexRepository

    //observed data
    private val user: MutableLiveData<Resource<UserLocal>> = MutableLiveData()
    private val lesson: MutableLiveData<Resource<LessonLocal>> = MutableLiveData()
    private val downloadedLesson: MutableLiveData<Resource<DownloadLocal>> = MutableLiveData()
    private val netStatus: MutableLiveData<Boolean> = MutableLiveData()

    //data observer
//    private val userObserver: GeneralObserver<Resource<UserLocal>>
//    private val lessonObserver: GeneralObserver<Resource<LessonLocal>>
//    private val downloadedLessonObserver: GeneralObserver<Resource<DownloadLocal>>

    init {
        DaggerMagicBox.create().poke(this)
//        userObserver = GeneralObserver(user) {}
//        lessonObserver = GeneralObserver(lesson) {}
//        downloadedLessonObserver = GeneralObserver(downloadedLesson) {}
    }

    fun getNetStatus() = netStatus

    fun setNetStatus(boolean: Boolean) {
        netStatus.value = boolean
    }

//    fun getUser(): LiveData<Resource<UserLocal>> {
//        loadUser()
//        return user
//    }

//    fun loadUser() = userObserver.load()

//    fun getLesson(): LiveData<Resource<LessonLocal>> {
//        loadLesson()
//        return lesson
//    }

//    fun loadLesson() = lessonObserver.load()

//    fun getDownloadedLesson(): LiveData<Resource<DownloadLocal>> {
//        loadDownloadedLesson()
//        return downloadedLesson
//    }

//    private fun loadDownloadedLesson() = downloadedLessonObserver.load()

}
