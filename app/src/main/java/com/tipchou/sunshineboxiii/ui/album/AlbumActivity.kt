package com.tipchou.sunshineboxiii.ui.album

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import com.tipchou.sunshineboxiii.ui.course.Materials
import kotlinx.android.synthetic.main.activity_album.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class AlbumActivity : BaseActivity() {

    private var files: List<File> = ArrayList()

    private var position: Int = 0

    override fun created(bundle: Bundle?) {
        val materials: Materials? = intent.getSerializableExtra("materials") as Materials
        if (materials != null) {
            val resourceList = sortResourceList(materials)
            val getFileAndName = GetFileAndName(resourceList).invoke()
            files = getFileAndName.getFiles()
        }
        showImageAndName(files, getPosition(), album_act_imageview1)
        setUpClickEvent()
    }

    private fun setUpClickEvent() {
        album_act_button1.setOnClickListener {
            if (getPosition() == files.size - 1) {
                Toast.makeText(this, "已经是最后一张图片了!", Toast.LENGTH_SHORT).show()
            } else {
                setPosition(getPosition() + 1)
                showImageAndName(files, getPosition(), album_act_imageview1)
            }
        }
        album_act_button2.setOnClickListener {
            if (getPosition() == 0) {
                Toast.makeText(this, "已经是第一张图片了！", Toast.LENGTH_SHORT).show()
            } else {
                setPosition(getPosition() - 1)
                showImageAndName(files, getPosition(), album_act_imageview1)
            }
        }
        album_act_button3.setOnClickListener { finish() }
    }

    private fun getPosition(): Int {
        return position
    }

    private fun setPosition(position: Int) {
        this.position = position
    }

    private fun showImageAndName(files: List<File>, position: Int, imageView: ImageView) {
        if (!files.isEmpty()) {
            if (position < files.size) {
                Glide.with(this).load(files[position]).into(imageView)
            }
        }
    }

    private fun sortResourceList(materials: Materials): List<Materials.AlbumResource> {
        val resourceList = materials.albumResourceList
        resourceList.sortWith(Comparator { o1, o2 -> o1.order - o2.order })
        return resourceList
    }

    override fun resume() {
    }

    override fun layoutId(): Int = R.layout.activity_album

    private inner class GetFileAndName internal constructor(private val resourceList: List<Materials.AlbumResource>) {
        private var files: MutableList<File> = ArrayList()
        private var names: MutableList<String> = ArrayList()

        internal fun getFiles(): List<File> {
            return files
        }

        internal fun getNames(): List<String> {
            return names
        }

        internal operator fun invoke(): GetFileAndName {
            files = ArrayList()
            names = ArrayList()
            for (resource in resourceList) {
                val file = File(resource.resourceStorageAddress)
                files.add(file)
                var file_name = resource.name
                if (file_name == null)
                {
                    file_name = file.name
                }
                names.add(file_name)
            }
            return this
        }
    }
}
