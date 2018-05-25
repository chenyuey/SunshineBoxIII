package com.tipchou.sunshineboxiii.ui.favorite

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tipchou.sunshineboxiii.entity.local.FavoriteLocal
import com.tipchou.sunshineboxiii.entity.local.LessonLocal
import com.tipchou.sunshineboxiii.support.DaggerMagicBox
import com.tipchou.sunshineboxiii.support.GeneralObserver
import com.tipchou.sunshineboxiii.support.Repository
import com.tipchou.sunshineboxiii.support.Resource
import javax.inject.Inject

/**
 * Created by 邵励治 on 2018/5/24.
 * Perfect Code
 */
class FavoriteViewModel : ViewModel() {
    @Inject
    lateinit var repository: Repository

    private val favorite: MutableLiveData<Resource<List<FavoriteLocal>>> = MutableLiveData()
    private val favoriteObserver: GeneralObserver<Resource<List<FavoriteLocal>>>
    private val favoriteLesson: MutableLiveData<Resource<List<LessonLocal>>> = MutableLiveData()
    private val lessonObserver: GeneralObserver<Resource<List<LessonLocal>>>

    init {
        DaggerMagicBox.create().poke(this)
        favoriteObserver = GeneralObserver(favorite) {
            repository.getFavorite()
        }
        lessonObserver = GeneralObserver(favoriteLesson) {
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
    }

    fun getFavorite(): LiveData<Resource<List<FavoriteLocal>>> {
        loadFavorite()
        return favorite
    }

    fun getFavoriteLesson(): LiveData<Resource<List<LessonLocal>>> {
        loadFavoriteLesson()
        return favoriteLesson
    }

    fun loadFavorite() {
        favoriteObserver.load()
    }

    fun loadFavoriteLesson() {
        lessonObserver.load()
    }

}