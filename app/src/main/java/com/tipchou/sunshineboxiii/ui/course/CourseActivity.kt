package com.tipchou.sunshineboxiii.ui.course

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.support.IOUtils
import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_course.*
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

class CourseActivity : BaseActivity() {
    private var resourceStorageAddress: String? = null

    override fun layoutId(): Int = R.layout.activity_course

    override fun created(bundle: Bundle?) {
        //获取resource存储地址
        resourceStorageAddress = intent.getStringExtra(RESOURCE_STORAGE_ADDRESS)
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
        }
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
        val regex = "\\[(\\S+)]\\((\\S+)\\)".toRegex()
        val markdown = bean.content.replace(regex, markdownReplace)
        Log.e("Markdown content", markdown)
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

    override fun resume() {

    }

    companion object {
        private const val RESOURCE_STORAGE_ADDRESS = "resource storage address"
        fun newIntent(packageContext: Context, resourceStorageAddress: String): Intent {
            val intent = Intent(packageContext, CourseActivity::class.java)
            intent.putExtra(RESOURCE_STORAGE_ADDRESS, resourceStorageAddress)
            return intent
        }
    }
}
