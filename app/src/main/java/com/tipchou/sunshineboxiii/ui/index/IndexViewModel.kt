package com.tipchou.sunshineboxiii.ui.index

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tipchou.sunshineboxiii.entity.local.DownloadLocal
import com.tipchou.sunshineboxiii.entity.local.LessonLocal
import com.tipchou.sunshineboxiii.entity.local.RoleLocal
import com.tipchou.sunshineboxiii.support.DaggerMagicBox
import com.tipchou.sunshineboxiii.support.GeneralObserver
import com.tipchou.sunshineboxiii.support.LessonType
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
    private val role: MutableLiveData<Resource<List<RoleLocal>>> = MutableLiveData()
    private val lesson: MutableLiveData<Resource<List<LessonLocal>>> = MutableLiveData()
    private val downloadedLesson: MutableLiveData<List<DownloadLocal>> = MutableLiveData()
    private val netStatus: MutableLiveData<Boolean> = MutableLiveData()
    private val lessonType: MutableLiveData<LessonType> = MutableLiveData()
    private val downloadProcess: MutableLiveData<Boolean> = MutableLiveData()

    //data observer
    private val roleObserver: GeneralObserver<Resource<List<RoleLocal>>>
    private val lessonObserver: GeneralObserver<Resource<List<LessonLocal>>>
    private val downloadedLessonObserver: GeneralObserver<List<DownloadLocal>>

    init {
        DaggerMagicBox.create().poke(this)
        lessonType.value = LessonType.NURSERY
        roleObserver = GeneralObserver(role) {
            repository.getUserRole()
        }
        lessonObserver = GeneralObserver(lesson) {
            repository.getLesson(lessonType = lessonType.value!!)
        }
        downloadedLessonObserver = GeneralObserver(downloadedLesson) {
            repository.getDownload()
        }
    }

    fun getDownloadProcess() = downloadProcess

    fun setDownloadProcess() {
        downloadProcess.value = downloadProcess.value != true
    }

    fun getLessonType() = lessonType

    fun setLessonType(lessonType: LessonType) {
        this.lessonType.value = lessonType
    }

    fun getNetStatus() = netStatus

    fun setNetStatus(boolean: Boolean) {
        netStatus.value = boolean
    }

    fun getRole(): MutableLiveData<Resource<List<RoleLocal>>> {
        loadRole()
        return role
    }

    fun loadRole() {
        roleObserver.load()
    }

    fun getLesson(): MutableLiveData<Resource<List<LessonLocal>>> {
        loadLesson()
        return lesson
    }

    fun loadLesson() {
        lessonObserver.load()
    }

    fun getDownloadedLesson(): MutableLiveData<List<DownloadLocal>> {
        loadDownloadedLesson()
        return downloadedLesson
    }

    fun loadDownloadedLesson() {
        downloadedLessonObserver.load()
    }
}
