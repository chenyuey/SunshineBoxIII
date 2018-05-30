package com.tipchou.sunshineboxiii.ui.specialdetail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tipchou.sunshineboxiii.entity.local.DownloadLocal
import com.tipchou.sunshineboxiii.entity.local.LessonLocal
import com.tipchou.sunshineboxiii.entity.local.LessonSubjectLocal
import com.tipchou.sunshineboxiii.support.DaggerMagicBox
import com.tipchou.sunshineboxiii.support.GeneralObserver
import com.tipchou.sunshineboxiii.support.Repository
import com.tipchou.sunshineboxiii.support.Resource
import javax.inject.Inject

/**
 * Created by 邵励治 on 2018/5/30.
 * Perfect Code
 */
class SpecialDetailViewModel : ViewModel() {
    @Inject
    lateinit var repository: Repository

    private val lessonSubject = MutableLiveData<Resource<List<LessonSubjectLocal>>>()
    private val lessonSubjectObserver: GeneralObserver<Resource<List<LessonSubjectLocal>>>
    private val lesson = MutableLiveData<Resource<List<LessonLocal>>>()
    private val lessonObserver: GeneralObserver<Resource<List<LessonLocal>>>
    private val download = MutableLiveData<List<DownloadLocal>>()
    private val downloadObserver: GeneralObserver<List<DownloadLocal>>
    private var specialObjectId: String = ""


    init {
        DaggerMagicBox.create().poke(this)
        lessonSubjectObserver = GeneralObserver(lessonSubject) {
            repository.getLessonSubject(specialObjectId)
        }
        lessonObserver = GeneralObserver(lesson) {
            val objectIdList = arrayListOf<String>()
            val lessonSubjectList = lessonSubject.value?.data
            if (lessonSubjectList == null) {
                //do nothing
            } else {
                for (lessonSubject in lessonSubjectList) {
                    objectIdList.add(lessonSubject.lessonObjectId)
                }
            }
            repository.getLesson(objectIdList)
        }
        downloadObserver = GeneralObserver(download) {
            repository.getDownload()
        }
    }

    fun getLessonSubject(specialObjectId: String): MutableLiveData<Resource<List<LessonSubjectLocal>>> {
        loadLessonSubject(specialObjectId)
        return lessonSubject
    }

    fun loadLessonSubject(specialObjectId: String) {
        this.specialObjectId = specialObjectId
        lessonSubjectObserver.load()
    }

    fun getDownload(): LiveData<List<DownloadLocal>> {
        loadDownload()
        return download
    }

    fun loadDownload() {
        downloadObserver.load()
    }

    fun getLesson(): LiveData<Resource<List<LessonLocal>>> {
        return lesson
    }

    fun loadLesson() {
        lessonObserver.load()
    }
}
