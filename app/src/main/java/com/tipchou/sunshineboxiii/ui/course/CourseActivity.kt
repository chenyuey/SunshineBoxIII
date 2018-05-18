package com.tipchou.sunshineboxiii.ui.course

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.avos.avoscloud.AVCloud
import com.avos.avoscloud.AVException
import com.avos.avoscloud.FunctionCallback
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.entity.local.FavoriteActionLocal
import com.tipchou.sunshineboxiii.support.IOUtils
import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import com.tipchou.sunshineboxiii.ui.index.IndexViewModel
import com.tipchou.sunshineboxiii.ui.video.VideoActivity
import kotlinx.android.synthetic.main.activity_course.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

class CourseActivity : BaseActivity(), CourseMediaPlayer {
    private val mediaPlayer: MediaPlayer = MediaPlayer()

    private var resourceStorageAddress: String? = null

    private var handlerSwitch: Boolean = true

    private var lessonObjectId: String? = null

    private var viewModel: IndexViewModel? = null

    private fun setUpClickEvent() {
        //back
        course_act_imagebutton.setOnClickListener {
            handlerSwitch = false
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.release()
            }
            finish()
        }
        //music-player replay
        course_act_imageview2.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.seekTo(0)
            }
        }
        //music-player play or pause
        course_act_imageview1.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                //此时没有播放
                mediaPlayer.start()
                Glide.with(this).load(R.drawable.pause).into(course_act_imageview1)
            } else {
                mediaPlayer.pause()
                Glide.with(this).load(R.drawable.play).into(course_act_imageview1)
            }
        }
        //music-player close
        course_act_imageview3.setOnClickListener {
            mediaPlayer.reset()
            course_act_cardview1.visibility = View.GONE
        }
        //click favorite
        course_act_imageview4.setOnClickListener {
            if (favorite) {
                val parameters = HashMap<String, ArrayList<FavoriteActionLocal>>()
                val list = arrayListOf<FavoriteActionLocal>()
                list.add(FavoriteActionLocal(lessonObjectId!!, System.currentTimeMillis(), false))
                parameters["collectionActionArr"] = list

                AVCloud.callFunctionInBackground("collection", parameters, object : FunctionCallback<HashMap<String, String>>() {
                    override fun done(p0: HashMap<String, String>?, p1: AVException?) {
                        viewModel?.loadFavorite()
                    }
                })
            } else {
                val parameters = HashMap<String, ArrayList<FavoriteActionLocal>>()
                val list = arrayListOf<FavoriteActionLocal>()
                list.add(FavoriteActionLocal(lessonObjectId!!, System.currentTimeMillis(), true))
                parameters["collectionActionArr"] = list

                AVCloud.callFunctionInBackground("collection", parameters, object : FunctionCallback<HashMap<String, String>>() {
                    override fun done(p0: HashMap<String, String>?, p1: AVException?) {
                        viewModel?.loadFavorite()
                    }
                })
            }
        }
    }

    override fun onBackPressed() {
        handlerSwitch = false

        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        super.onBackPressed()
    }

    override fun layoutId(): Int = R.layout.activity_course

    override fun created(bundle: Bundle?) {
        setUpClickEvent()
        //获取resource存储地址
        resourceStorageAddress = intent.getStringExtra(RESOURCE_STORAGE_ADDRESS)
        lessonObjectId = intent.getStringExtra(LESSON_OBJECT_ID)
        //根据resource的地址构建Json和Material的存储地址
        val jsonStorageAddress: String = resourceStorageAddress + File.separator + "manifest.json"
        val materialStorageAddress: String = resourceStorageAddress + File.separator + "materials"
        //根据Json和Material的存储地址构建JsonFile和MaterialFile
        val jsonFile: File = getJsonFile(jsonStorageAddress)
        val materialFolder: File = getMaterialFolder(materialStorageAddress)
        //解析JSON到Bean中
        val jsonBean: CourseBean? = deserializeJson(jsonFile)
        if (jsonBean == null) {
            //should not be here
            Toast.makeText(this, "读取失败！请尝试重置软件，按钮在首页的侧边栏里！", Toast.LENGTH_SHORT).show()
        } else {
            //读取bean内容到界面中
            course_act_textview1.text = jsonBean.name
            course_act_textview3.text = jsonBean.source

            //解析markdown并加载
            if (jsonBean.content != null) {
                val markdown: String = rebuildMarkdown(materialFolder, jsonBean)
                course_act_markdownview1.loadMarkdown(markdown)
            }

            //加载Material中数据到RecyclerView中
            val courseAdapter = CourseAdapter(this)
            course_act_recyclerview.layoutManager = LinearLayoutManager(this)
            course_act_recyclerview.adapter = courseAdapter

            if (jsonBean.materials != null) {
                val materialsList = getMaterialsList(materialFolder, jsonBean)
                courseAdapter.setMaterialData(materialsList)
            }
            setUpViewModel()
        }
    }

    var favorite: Boolean = false

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(IndexViewModel::class.java)
        viewModel?.getFavorite()?.observe(this, android.arch.lifecycle.Observer {
            val data = it?.data
            if (data != null) {
                for (item in data) {
                    if (item.lessonId == lessonObjectId) {
                        favorite = when (item.action) {
                            true -> {
                                course_act_imageview4.setBackgroundResource(R.drawable.favourite_red)
                                true
                            }
                            false -> {
                                course_act_imageview4.setBackgroundResource(R.drawable.default_favorite)
                                false
                            }
                        }
                    }
                }
            }
        })
    }

    override fun resume() {

    }

    private val handler: Handler = MyHandler(this)

    class MyHandler constructor(courseActivity: CourseActivity) : Handler() {
        private val weakReference = WeakReference<CourseActivity>(courseActivity)

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val activity = weakReference.get()
            if (activity != null) {
                if (activity.handlerSwitch) {
                    if (msg?.what == 100) {
                        activity.course_act_textview6.text = activity.getFormatTime(activity.mediaPlayer.currentPosition)
                    }
                } else {

                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun playAudio(materials: Materials) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.reset()
        }
        val uri = Uri.parse(materials.resourceStorageAddress)
        course_act_cardview1.visibility = View.VISIBLE
        course_act_textview5.text = materials.name
        course_act_textview6.text = "00:00"
        course_act_textview7.text = "00:00"

        Glide.with(this).load(R.drawable.pause).into(course_act_imageview1)

        try {
            mediaPlayer.setDataSource(uri.toString())
            mediaPlayer.prepare()
            mediaPlayer.start()

            course_act_textview7.text = getFormatTime(mediaPlayer.duration)

            val timer = Timer()
            val timerTask = object : TimerTask() {
                override fun run() {
                    handler.sendEmptyMessage(100)
                }

            }
            timer.schedule(timerTask, 0, 10)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    override fun playVideo(materials: Materials) {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.reset()
            course_act_cardview1.visibility = View.GONE
        }
        val intent = VideoActivity.newIntent(this, materials.resourceStorageAddress)
        startActivity(intent)
    }

    override fun openAlbum(materials: Materials) {
    }

    private fun getMaterialsList(materialFolder: File, bean: CourseBean): List<Materials> {
        val materialsList = ArrayList<Materials>()
        for (materialsBean in bean.materials) {
            //非图集内图片
            if (materialsBean.parent == null) {
                val materials = Materials()
                when (materialsBean.type) {
                    0 -> {
                        //图集
                        materials.name = materialsBean.name
                        materials.materialType = Materials.MaterialType.ALBUM
                        materials.resourceStorageAddress = materialFolder.absolutePath
                        //获取图集的儿子们
                        val albumResourceList = ArrayList<Materials.AlbumResource>()
                        for (materialsBean1 in bean.materials) {
                            if (materialsBean1.parent != null) {
                                if (materialsBean1.parent == materialsBean.id) {
                                    val albumResource = Materials.AlbumResource()
                                    albumResource.name = materialsBean1.name
                                    albumResource.order = materialsBean1.album_index
                                    albumResource.resourceStorageAddress = materialFolder.absolutePath + File.separator + materialsBean1.filename
                                    albumResourceList.add(albumResource)
                                }
                            }
                        }
                        materials.albumResourceList = albumResourceList
                        materials.order = materialsBean.file_index
                        materialsList.add(materials)
                    }
                    1 -> {
                        //音频
                        materials.name = materialsBean.name
                        materials.materialType = Materials.MaterialType.AUDIO
                        materials.resourceStorageAddress = materialFolder.absolutePath + File.separator + materialsBean.filename
                        materials.order = materialsBean.file_index
                        materialsList.add(materials)
                    }
                    2 -> {
                        //视频
                        materials.name = materialsBean.name
                        materials.materialType = Materials.MaterialType.VIDEO
                        materials.resourceStorageAddress = materialFolder.absolutePath + File.separator + materialsBean.filename
                        materials.order = materialsBean.file_index
                        materialsList.add(materials)
                    }
                    3 -> {
                    }
                    else -> {
                    }
                }//markdown内图片, do nothing

            }
        }
        return materialsList
    }

    private fun rebuildMarkdown(materialFolder: File, bean: CourseBean): String {
        val markdownReplace = "[$1](" + "file://" + materialFolder.absolutePath + "/$2)"
        val markdownReplaces = "<img src=" + "file://" + materialFolder.absolutePath + "/$2" + " width=\"520px\"/>"
        val regex = "!\\[(\\S+)]\\((\\S+)\\)".toRegex()
        Log.e("replaceBefore", bean.content)
        val markdown = bean.content.replace(regex, markdownReplaces)
        Log.e("replaceAfter", markdown)
        return markdown
    }

    private fun deserializeJson(jsonFile: File): CourseBean? {
        var bean: CourseBean? = null
        try {
            val fileInputStream = FileInputStream(jsonFile)
            val json = IOUtils.fileToString(fileInputStream)
            val gson = Gson()
            bean = gson.fromJson<CourseBean>(json, CourseBean::class.java)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bean
    }

    private fun getJsonFile(jsonStorageAddress: String): File {
        val jsonFile = File(jsonStorageAddress)
        if (jsonFile.exists()) {
            Log.e("CourseActivity", "Get Json File")
        } else {
            Log.e("CourseActivity", "Not Get Json File")
        }
        return jsonFile
    }

    private fun getMaterialFolder(materialStorageAddress: String): File {
        val materialFolder = File(materialStorageAddress)
        if (materialFolder.exists()) {
            Log.e("CourseActivity", "Get MaterialFolder")
        } else {
            Log.e("CourseActivity", "Not Get MaterialFolder")
        }
        return materialFolder
    }

    @SuppressLint("SimpleDateFormat")
    private fun getFormatTime(time: Int): String {
        val date = Date(time.toLong())
        val simpleDateFormat = SimpleDateFormat("mm:ss")
        return simpleDateFormat.format(date)
    }

    companion object {
        private const val RESOURCE_STORAGE_ADDRESS = "resource storage address"
        private const val LESSON_OBJECT_ID = "lesson object id"
        fun newIntent(packageContext: Context, lessonObjectId: String, resourceStorageAddress: String): Intent {
            val intent = Intent(packageContext, CourseActivity::class.java)
            intent.putExtra(RESOURCE_STORAGE_ADDRESS, resourceStorageAddress)
            intent.putExtra(LESSON_OBJECT_ID, lessonObjectId)
            return intent
        }
    }
}
