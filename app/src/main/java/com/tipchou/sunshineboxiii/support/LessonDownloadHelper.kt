package com.tipchou.sunshineboxiii.support

import android.arch.lifecycle.MediatorLiveData
import android.os.Environment
import com.tipchou.sunshineboxiii.entity.DownloadHolder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

/**
 * Created by 邵励治 on 2018/5/14.
 * Perfect Code
 */
class LessonDownloadHelper
constructor(saveDownloadResult: (lessonObjectId: String, storageUrl: String, editor: Boolean) -> Unit) {
    private val downloadQueue = MediatorLiveData<HashMap<DownloadHolder, String>>()
    private var isDownloading: Boolean = false
    private val service = ServiceGenerator.createService(RetrofitWebService::class.java)

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
                        val call: Call<ResponseBody> = service.downloadFile(downloadHolder.downloadUrl)
                        call.enqueue(object : Callback<ResponseBody> {
                            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                                when (response?.isSuccessful) {
                                    true -> {
                                        val folder: File = if (downloadHolder.editor) {
                                            File(Environment.getExternalStorageDirectory().path + File.separator + "Sunshinebox_III" + File.separator + "editor")
                                        } else {
                                            File(Environment.getExternalStorageDirectory().path + File.separator + "Sunshinebox_III" + File.separator + "normal")
                                        }

                                        DownloadTask(response, downloadHolder.lessonObjectId + ".zip", folder, object : DownloadTask.DownloadCallback {
                                            override fun downloadSuccess(file: File) {
                                                saveDownloadResult(downloadHolder.lessonObjectId, file.absolutePath, downloadHolder.editor)
                                                isDownloading = false
                                                it.remove(downloadHolder)
                                            }

                                            override fun downloadFailure(file: File) {
                                                //下载失败？
                                            }

                                            override fun progressUpdate(value: Long?) {
                                                it[downloadHolder] = "正在下载：$value%"
                                            }

                                        }).execute()
                                    }
                                    false -> {
                                        //下载失败？
                                    }
                                }
                            }

                            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                                //下载失败？
                            }
                        })
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

    fun getDownloadQueue() = downloadQueue
}