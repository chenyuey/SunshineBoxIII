package com.tipchou.sunshineboxiii.ui.test

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.avos.avoscloud.AVException
import com.avos.avoscloud.AVUser
import com.avos.avoscloud.LogInCallback
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.R.id.main_act_textview1
import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_test.*
import java.io.File

class TestActivity : BaseActivity() {
    private var viewModel: TestViewModel? = null

    var downloadManager: DownloadManager? = null

    var downloadObserver: DownloadObserver? = null

    override fun layoutId(): Int = R.layout.activity_test

    @SuppressLint("SetTextI18n")
    override fun created(bundle: Bundle?) {
        viewModel = ViewModelProviders.of(this).get(TestViewModel::class.java)

        viewModel?.getUser()?.observe(this, Observer {
            Log.e("Fuck", "Status: ${it?.status.toString()}; Message: ${it?.message}; Size: ${it?.data?.size}")
            if (it == null) {

            } else {
                if (it.data == null) {

                } else {
                    main_act_textview1.text = ""
                    for (item in it.data) {
                        main_act_textview1.text = "${main_act_textview1.text}\n${item.userName}"
                        Log.e("Item", "${item.tags}")
                    }
                }
            }
        })

        main_act_textview1.setOnClickListener {
            Log.e("Fuck", "onClick")
            viewModel?.loadUser()
        }
//        AVUser.logInInBackground("shaolizhi", "12345678", object : LogInCallback<AVUser>() {
//            override fun done(p0: AVUser?, p1: AVException?) {
//
//            }
//        })
        val rootFolder = createRootFolder()
        //0.得到下载地址
        val imageUri = Uri.parse("http://lc-cqbvih8f.cn-n1.lcfile.com/b88aaa2cb784398e5559.jpg")
        //1.得到下载对象
        downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        //2.创建下载请求对象，并将下载地址放进去
        val request = DownloadManager.Request(imageUri)
        //3.给下载的文件指定路径
        request.setDestinationInExternalPublicDir("SunshineBox_III", "1.jpg")
        //4.设置为不在通知栏可见
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
        //5.设置为可见和可管理
        request.setVisibleInDownloadsUi(false)
        //6.获取downloadId
        val downloadId = downloadManager?.enqueue(request)
        downloadObserver = DownloadObserver(null, this, downloadId!!)
        contentResolver.registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, downloadObserver)

    }


    class DownloadObserver(handler: Handler?, private val activity: Activity, private val downloadId: Long) : ContentObserver(handler) {
        var boolean = true

        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)

            boolean = boolean xor true
            Log.e("boolean", boolean.toString())
            val query = DownloadManager.Query()
//            query.setFilterById(downloadId)
            val downloadManager: DownloadManager = activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val cursor: Cursor? = downloadManager.query(query)
            if (cursor == null) {
                //should not be here
            } else {
                cursor.moveToFirst()
                val totalColumn: Int = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                val currentColumn: Int = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                val totalSize: Int = cursor.getInt(totalColumn)
                val currentSize: Int = cursor.getInt(currentColumn)
                val percent: Float = currentSize.toFloat() / totalSize.toFloat()
                val progress = Math.round(percent * 100)
                if (progress == 100) {
                    Log.e("download", "done!!!")
                } else {
                    Log.e("download", progress.toString())
                }
                if (downloadManager.getUriForDownloadedFile(downloadId) != null) {
                    Log.e("download", "success!!! = ${downloadManager.getUriForDownloadedFile(downloadId)}")
                    Log.e("local_url", cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)))
                    Log.e("id", cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_ID)))
                }
            }
        }
    }


    private fun createRootFolder(): File {
        val file = File(Environment.getExternalStorageDirectory().path + File.separator + "SunshineBox_III")
        if (!file.exists()) {
            Log.e("Create Folder: ", file.mkdirs().toString())
        }
        return file
    }

    private fun checkPermissions(): Boolean {
        //检查运行时权限
        val result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED
        val result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        //result1 = true 说明缺少权限
        Log.e("result1", result1.toString())
        Log.e("result2", result2.toString())

        //有一个为true（缺少权限），就需要申请
        if (result2) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
        return !result2
    }

    fun getBytesAndStatus(downloadId: Long, downloadManager: DownloadManager): IntArray {
        val bytesAndStatus = intArrayOf(-1, -1, 0)
        val query = DownloadManager.Query().setFilterById(downloadId)
        var cursor: Cursor? = null
        try {
            cursor = downloadManager.query(query)
            if (cursor != null && cursor.moveToFirst()) {
                bytesAndStatus[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                bytesAndStatus[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                bytesAndStatus[2] = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            }
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
        return bytesAndStatus
    }

    override fun resume() {
    }

    override fun onDestroy() {
        super.onDestroy()
        contentResolver.unregisterContentObserver(downloadObserver)
    }
}
