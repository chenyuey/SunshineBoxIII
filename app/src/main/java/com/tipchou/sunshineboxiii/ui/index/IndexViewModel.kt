package com.tipchou.sunshineboxiii.ui.index

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tipchou.sunshineboxiii.entity.local.DownloadLocal
import com.tipchou.sunshineboxiii.entity.local.FavoriteLocal
import com.tipchou.sunshineboxiii.entity.local.LessonLocal
import com.tipchou.sunshineboxiii.entity.local.RoleLocal
import com.tipchou.sunshineboxiii.support.*
import javax.inject.Inject

/**
 * Created by 邵励治 on 2018/4/29.
 * Perfect Code
 */
class IndexViewModel : ViewModel() {
    @Inject
    lateinit var repository: Repository

    //observed data
    private val role: MutableLiveData<Resource<List<RoleLocal>>> = MutableLiveData()
    private val lesson: MutableLiveData<Resource<List<LessonLocal>>> = MutableLiveData()
    private val favorite: MutableLiveData<Resource<List<FavoriteLocal>>> = MutableLiveData()
    private val downloadedLesson: MutableLiveData<List<DownloadLocal>> = MutableLiveData()
    private val netStatus: MutableLiveData<Boolean> = MutableLiveData()
    private val lessonType: MutableLiveData<LessonType> = MutableLiveData()
    //data observer
    private val roleObserver: GeneralObserver<Resource<List<RoleLocal>>>
    private val lessonObserver: GeneralObserver<Resource<List<LessonLocal>>>
    private val favoriteObserver: GeneralObserver<Resource<List<FavoriteLocal>>>
    private val downloadedLessonObserver: GeneralObserver<List<DownloadLocal>>

    private val lessonDownloadHelper = LessonDownloadHelper { lessonObjectId, storageUrl, editor ->
        if (editor) {
            repository.dbDao.saveDownload(lessonObjectId, null, storageUrl)
        } else {
            repository.dbDao.saveDownload(lessonObjectId, storageUrl, null)
        }
    }

    init {
        DaggerMagicBox.create().poke(this)
        lessonType.value = LessonType.NURSERY
        roleObserver = GeneralObserver(role) {
            repository.getUserRole()
        }
        lessonObserver = GeneralObserver(lesson) {
            repository.getLesson(lessonType = lessonType.value!!)
        }
        favoriteObserver = GeneralObserver(favorite) {
            repository.getFavorite()
        }
        downloadedLessonObserver = GeneralObserver(downloadedLesson) {
            repository.getDownload()
        }
    }

    fun getFavorite(): MutableLiveData<Resource<List<FavoriteLocal>>> {
        loadFavorite()
        return favorite
    }

    fun loadFavorite() {
        favoriteObserver.load()
    }

    fun getDownloadQueue() = lessonDownloadHelper.getDownloadQueue()

    fun downloadLesson(downloadHolder: DownloadHolder) = lessonDownloadHelper.enqueueDownloaded(downloadHolder)

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

    fun clearDatabase() {
        repository.dbDao.removeAll()
    }
}
