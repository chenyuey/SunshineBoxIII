package com.tipchou.sunshineboxiii.ui.index

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.entity.local.DownloadLocal
import com.tipchou.sunshineboxiii.entity.local.LessonLocal
import com.tipchou.sunshineboxiii.entity.local.RoleLocal
import com.tipchou.sunshineboxiii.support.DaggerMagicBox
import com.tipchou.sunshineboxiii.support.Resource
import com.tipchou.sunshineboxiii.support.dao.DbDao
import javax.inject.Inject

/**
 * Created by 邵励治 on 2018/5/8.
 * Perfect Code
 */
class IndexRecyclerViewAdapter(private val activity: IndexActivity) : RecyclerView.Adapter<IndexRecyclerViewAdapter.ViewHolder>() {
    class ItemData(val LessonLocal: LessonLocal, val editor: Boolean, val download: Boolean)

    @Inject
    lateinit var dbDao: DbDao

    private val netStatusLiveData: LiveData<Boolean>
    private val roleLiveData: LiveData<Resource<List<RoleLocal>>>
    private val lessonLiveData: LiveData<Resource<List<LessonLocal>>>
    private val downloadedLessonLiveData: LiveData<List<DownloadLocal>>

    private val layoutInflater: LayoutInflater = LayoutInflater.from(activity)

    private val itemDataList = ArrayList<ItemData>()

    init {
        DaggerMagicBox.create().poke(this)
        val viewModel: IndexViewModel = ViewModelProviders.of(activity).get(IndexViewModel::class.java)
        netStatusLiveData = viewModel.getNetStatus()
        netStatusLiveData.observe(activity, Observer { })
        downloadedLessonLiveData = viewModel.getDownloadedLesson()
        downloadedLessonLiveData.observe(activity, Observer { })
        roleLiveData = viewModel.getRole()
        roleLiveData.observe(activity, Observer { })
        lessonLiveData = viewModel.getLesson()
        lessonLiveData.observe(activity, Observer {
            itemDataList.clear()
//            notifyDataSetChanged()
            buildLessonList(it)
            notifyDataSetChanged()

            if (itemDataList.size == 0) {
                activity.showNoDataHint(true)
            } else {
                activity.showNoDataHint(false)
            }
        })
    }

    private fun buildLessonList(it: Resource<List<LessonLocal>>?) {
        if (it?.status == Resource.Status.SUCCESS || it?.status == Resource.Status.ERROR || it?.status == Resource.Status.LOADING) {
            val roleList: List<RoleLocal>? = roleLiveData.value?.data
            val downloadedLessonList: List<DownloadLocal>? = downloadedLessonLiveData.value
            val lessonList = it.data
            if (roleList == null) {
                //should not be here
            } else {
                if (downloadedLessonList == null) {
                    //should not be here
                } else {
                    val isEditor = isUserEditor(roleList)
                    if (isEditor) {
                        if (lessonList == null) {
                            //should not be here
                        } else {
                            for (lesson in lessonList) {
                                if (lesson.areChecked == 1) {
                                    var download = false
                                    for (downloadedLesson in downloadedLessonList) {
                                        if (downloadedLesson.objectId == lesson.objectId) {
                                            if (downloadedLesson.stagingUrl != null) {
                                                download = true
                                            }
                                        }
                                    }
                                    itemDataList.add(ItemData(lesson, true, download))
                                }
                            }
                            for (lesson in lessonList) {
                                if (lesson.isPublish == true) {
                                    var download = false
                                    for (downloadedLesson in downloadedLessonList) {
                                        if (downloadedLesson.objectId == lesson.objectId) {
                                            if (downloadedLesson.stagingUrl != null) {
                                                download = true
                                            }
                                        }
                                    }
                                    itemDataList.add(ItemData(lesson, false, download))
                                }
                            }
                        }
                    } else {
                        if (lessonList == null) {
                            //should not be here
                        } else {
                            for (lesson in lessonList) {
                                if (lesson.isPublish == true) {
                                    var download = false
                                    for (downloadedLesson in downloadedLessonList) {
                                        if (downloadedLesson.objectId == lesson.objectId) {
                                            if (downloadedLesson.publishedUrl != null) {
                                                download = true
                                            }
                                        }
                                    }
                                    itemDataList.add(ItemData(lesson, false, download))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun isUserEditor(roleList: List<RoleLocal>): Boolean {
        var isEditor = false
        for (role in roleList) {
            when (role.name) {
                "admin" -> isEditor = true
                "admin1" -> isEditor = true
                "admin2" -> isEditor = true
            }
        }
        return isEditor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(layoutInflater.inflate(R.layout.item_index_recyclerview, parent, false))

    override fun getItemCount(): Int = itemDataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemData = itemDataList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val backgroundImageView: ImageView
        private val isAuditedImageView: ImageView
        private val downloadStatusTextView: TextView
        private val lessonNameTextView: TextView

        //useful data
        private var lesson: LessonLocal? = null
        private var editor: Boolean? = null
        private var download: Boolean? = null

        fun bind(itemData: ItemData?) {
            getUsefulData(itemData)
            val editor = this.editor
                    ?: throw Exception("IndexRecyclerViewAdapter's bind() editor is null")
            val lesson = this.lesson
                    ?: throw Exception("IndexRecyclerViewAdapter's bind() lesson is null")
            val download = this.download
                    ?: throw Exception("IndexRecyclerViewAdapter's bind() download is null")

            //now we get all useful data
            setUpLessonName()
            setUpEditorTip()
            if (download) {
                when (lesson.subject) {
                    "NURSERY" -> backgroundImageView.setBackgroundResource(R.drawable.nursery)
                    "MUSIC" -> backgroundImageView.setBackgroundResource(R.drawable.music)
                    "READING" -> backgroundImageView.setBackgroundResource(R.drawable.reading)
                    "GAME" -> backgroundImageView.setBackgroundResource(R.drawable.game)
                }
            } else {
                when (lesson.subject) {
                    "NURSERY" -> backgroundImageView.setBackgroundResource(R.drawable.nursery_gray)
                    "MUSIC" -> backgroundImageView.setBackgroundResource(R.drawable.music_gray)
                    "READING" -> backgroundImageView.setBackgroundResource(R.drawable.reading_gray)
                    "GAME" -> backgroundImageView.setBackgroundResource(R.drawable.game_gray)
                }
            }
        }

        private fun setUpEditorTip() {
            when (editor) {
                true -> {
                    isAuditedImageView.visibility = View.VISIBLE
                }
                false -> {
                    isAuditedImageView.visibility = View.GONE
                }
            }
        }

        private fun getUsefulData(itemData: ItemData?) {
            lesson = itemData?.LessonLocal
            editor = itemData?.editor
            download = itemData?.download
        }

        private fun setUpLessonName() {
            lessonNameTextView.text = lesson?.name
        }

        init {
            itemView.setOnClickListener(this)
            backgroundImageView = itemView.findViewById(R.id.index_rcv_imageview1)
            isAuditedImageView = itemView.findViewById(R.id.index_rcv_imageview2)
            downloadStatusTextView = itemView.findViewById(R.id.index_rcv_textview1)
            lessonNameTextView = itemView.findViewById(R.id.index_rcv_textview2)
        }

        override fun onClick(v: View?) {

        }
    }
}
