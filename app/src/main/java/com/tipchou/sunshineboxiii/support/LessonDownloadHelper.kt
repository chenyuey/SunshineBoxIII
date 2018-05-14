package com.tipchou.sunshineboxiii.support

import android.arch.lifecycle.MediatorLiveData
import com.tipchou.sunshineboxiii.entity.DownloadHolder

/**
 * Created by 邵励治 on 2018/5/14.
 * Perfect Code
 */
class LessonDownloadHelper {
    private val downloadQueue = MediatorLiveData<HashMap<DownloadHolder, String>>()
    private var isDownloading: Boolean = false


    init {
        downloadQueue.value = hashMapOf()
        downloadQueue.observeForever {
            if (it != null) {
                if (isDownloading) {
                    //当前正在下载
                    //do nothing
                } else {
                    var downloadHolder: DownloadHolder? = null
                    for (key in it.keys) {
                        downloadHolder = key
                        break
                    }
                    if (downloadHolder == null) {
                        //当前下载列表中没有元素
                        //do nothing
                    } else {
                        //根据downloadHolder中的信息进行下载
                        isDownloading = true

                    }
                }
            } else {
                //should not be here
            }
        }
    }

    fun enqueueDownloaded(downloadHolder: DownloadHolder) {
        downloadQueue.value?.put(downloadHolder, "等待下载中")
    }


}