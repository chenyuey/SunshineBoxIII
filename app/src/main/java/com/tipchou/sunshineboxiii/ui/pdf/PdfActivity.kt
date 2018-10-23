package com.tipchou.sunshineboxiii.ui.pdf

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.ui.base.BaseActivity
import com.tipchou.sunshineboxiii.ui.course.Materials
import kotlinx.android.synthetic.main.activity_pdf.*
import java.io.File
import kotlin.collections.ArrayList

class PdfActivity : BaseActivity() {

    private var files: List<File> = ArrayList()

    private var position: Int = 0

    override fun created(bundle: Bundle?) {
        val materials: Materials? = intent.getSerializableExtra("materials") as Materials
        if (materials != null) {
            Log.e("resourcrLies",materials.toString())

            val webSettings = webView.getSettings()
            webSettings.setJavaScriptEnabled(true)
            webSettings.setAllowFileAccess(true)
            webSettings.setAllowFileAccessFromFileURLs(true)
            webSettings.setAllowUniversalAccessFromFileURLs(true)
            webView.loadUrl("file:///android_asset/index.html?" + "http://lc-cqbvih8f.cn-n1.lcfile.com/0539e0fed43321508c13.pdf");
        }
        setUpClickEvent()
    }


    private fun setUpClickEvent() {
        pdf_close_act_button.setOnClickListener { finish() }
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

//    private fun sortResourceList(materials: Materials): List<Materials.AlbumResource> {
//        val resourceList = materials.albumResourceList
//        resourceList.sortWith(Comparator { o1, o2 -> o1.order - o2.order })
//        return resourceList
//    }

    override fun resume() {
    }

    override fun layoutId(): Int = R.layout.activity_pdf

//    private inner class GetFileAndName internal constructor(private val resourceList: List<Materials.AlbumResource>) {
//        private var files: MutableList<File> = ArrayList()
//        private var names: MutableList<String> = ArrayList()
//
//        internal fun getFiles(): List<File> {
//            return files
//        }
//
//        internal fun getNames(): List<String> {
//            return names
//        }
//
//        internal operator fun invoke(): GetFileAndName {
//            files = ArrayList()
//            names = ArrayList()
//            for (resource in resourceList) {
//                val file = File(resource.resourceStorageAddress)
//                files.add(file)
//                var file_name = resource.name
//                if (file_name == null)
//                {
//                    file_name = file.name
//                }
//                names.add(file_name)
//            }
//            return this
//        }
//    }
}