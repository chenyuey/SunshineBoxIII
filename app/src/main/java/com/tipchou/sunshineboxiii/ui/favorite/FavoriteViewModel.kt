package com.tipchou.sunshineboxiii.ui.favorite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tipchou.sunshineboxiii.entity.local.DownloadLocal
import com.tipchou.sunshineboxiii.entity.local.FavoriteLocal
import com.tipchou.sunshineboxiii.entity.local.LessonLocal
import com.tipchou.sunshineboxiii.support.*
import com.tipchou.sunshineboxiii.ui.index.DownloadHolder
import javax.inject.Inject

/**
 * Created by 邵励治 on 2018/5/24.
 * Perfect Code
 */
class FavoriteViewModel : ViewModel() {
    @Inject
    lateinit var repository: Repository

    private val favorite = MutableLiveData<Resource<List<FavoriteLocal>>>()
    private val favoriteObserver: GeneralObserver<Resource<List<FavoriteLocal>>>
    private val favoriteLesson = MutableLiveData<Resource<List<LessonLocal>>>()
    private val favoriteLessonObserver: GeneralObserver<Resource<List<LessonLocal>>>
    private val download = MutableLiveData<List<DownloadLocal>>()
    private val downloadObserver: GeneralObserver<List<DownloadLocal>>

    private val lessonDownloadHelper = LessonDownloadHelper { lessonObjectId, storageUrl, editor ->
        if (editor) {
            repository.dbDao.saveDownload(lessonObjectId, null, storageUrl)
        } else {
            repository.dbDao.saveDownload(lessonObjectId, storageUrl, null)
        }
    }

    fun getDownloadQueue() = lessonDownloadHelper.getDownloadQueue()

    fun downloadLesson(downloadHolder: DownloadHolder) = lessonDownloadHelper.enqueueDownloaded(downloadHolder)


    init {
        DaggerMagicBox.create().poke(this)
        favoriteObserver = GeneralObserver(favorite) {
            repository.getFavorite()
        }
        favoriteLessonObserver = GeneralObserver(favoriteLesson) {
            val objectIdList = arrayListOf<String>()
            val favoriteList = favorite.value?.data
            if (favoriteList == null) {
                //do nothing
            } else {
                for (favorite in favoriteList) {
                    objectIdList.add(favorite.lessonId)
                }
            }
            repository.getLesson(objectIdList)
        }
        downloadObserver = GeneralObserver(download) {
            repository.getDownload()
        }
    }

    fun getDownload(): LiveData<List<DownloadLocal>> {
        loadDownload()
        return download
    }

    fun loadDownload() {
        downloadObserver.load()
    }

    fun getFavorite(): LiveData<Resource<List<FavoriteLocal>>> {
        loadFavorite()
        return favorite
    }

    fun getFavoriteLesson(): LiveData<Resource<List<LessonLocal>>> {
        return favoriteLesson
    }

    fun loadFavorite() {
        favoriteObserver.load()
    }

    fun loadFavoriteLesson() {
        favoriteLessonObserver.load()
    }

}