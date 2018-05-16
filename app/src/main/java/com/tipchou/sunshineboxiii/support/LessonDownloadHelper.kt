package com.tipchou.sunshineboxiii.support

import android.arch.lifecycle.MediatorLiveData
import android.os.Environment
import android.util.Log
import com.tipchou.sunshineboxiii.ui.index.DownloadHolder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException

/**
 * Created by 邵励治 on 2018/5/14.
 * Perfect Code
 */
class LessonDownloadHelper
constructor(saveDownloadResult: (lessonObjectId: String, storageUrl: String, editor: Boolean) -> Unit) {
    private val downloadQueue = MediatorLiveData<HashMap<DownloadHolder, String>>()
    private var isDownloading: Boolean
    private val service = ServiceGenerator.createService(RetrofitWebService::class.java)

    init {
        downloadQueue.value = hashMapOf()
        isDownloading = false
        downloadQueue.observeForever {
            if (it != null) {
                Log.e("DownloadHelper", it.size.toString())
            }
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
                                                //将下载的文件解压缩!!!
                                                val outPutFolder = decompressZip(file, File(folder, downloadHolder.lessonObjectId))
                                                Log.e("outputFolder", outPutFolder.absolutePath)
                                                saveDownloadResult(downloadHolder.lessonObjectId, outPutFolder.absolutePath, downloadHolder.editor)
                                                isDownloading = false
                                                val newValue = hashMapOf<DownloadHolder, String>()
                                                newValue.putAll(downloadQueue.value!!)
                                                newValue.remove(downloadHolder)
                                                downloadQueue.value = newValue
                                            }

                                            override fun downloadFailure(file: File) {
                                                //下载失败？
                                            }

                                            override fun progressUpdate(value: Long?) {
                                                val newValue = hashMapOf<DownloadHolder, String>()
                                                newValue.putAll(downloadQueue.value!!)
                                                newValue[downloadHolder] = "正在下载：$value%"
                                                downloadQueue.value = newValue
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
        val newValue = hashMapOf<DownloadHolder, String>()
        newValue.putAll(downloadQueue.value!!)
        newValue[downloadHolder] = "等待下载中"
        downloadQueue.value = newValue
//        downloadQueue.value?.put(downloadHolder, "等待下载中")
    }

    fun getDownloadQueue() = downloadQueue

    private fun decompressZip(fileForUnzip: File, outputFolder: File): File {
        try {
            ZipUtils.unzip(fileForUnzip.absolutePath, outputFolder.absolutePath)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("LessonDownloadHelper", "解压失败！！！${fileForUnzip.absolutePath}")
        }
        return outputFolder
    }
}