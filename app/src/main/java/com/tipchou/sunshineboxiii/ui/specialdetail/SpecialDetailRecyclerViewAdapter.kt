package com.tipchou.sunshineboxiii.ui.specialdetail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.entity.local.DownloadLocal
import com.tipchou.sunshineboxiii.entity.local.LessonLocal
import com.tipchou.sunshineboxiii.ui.course.CourseActivity
import com.tipchou.sunshineboxiii.ui.index.DownloadHolder

/**
 * Created by 邵励治 on 2018/5/30.
 * Perfect Code
 */
class SpecialDetailRecyclerViewAdapter(private val activity: SpecialDetailActivity, specialObjectId: String) : RecyclerView.Adapter<SpecialDetailRecyclerViewAdapter.ViewHolder>() {
    private val viewModel: SpecialDetailViewModel = ViewModelProviders.of(activity).get(SpecialDetailViewModel::class.java)
    private val lessonList = arrayListOf<LessonLocal>()
    private val download: LiveData<List<DownloadLocal>>
    private val downloadingQueue: LiveData<HashMap<DownloadHolder, String>>

    private val toDownload: (downloadHolder: DownloadHolder) -> Unit

    init {
        viewModel.getLesson().observe(activity, Observer {
            val data = it?.data
            if (data != null) {
                lessonList.clear()
                for (item in data) {
                    if (item.isPublish == true) {
                        lessonList.add(item)
                    }
                }
                notifyDataSetChanged()
            }
        })
        viewModel.getLessonSubject(specialObjectId).observe(activity, Observer {
            viewModel.loadLesson()
        })
        download = viewModel.getDownload()
        downloadingQueue = viewModel.getDownloadQueue()
        toDownload = { viewModel.downloadLesson(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_index_recyclerview, parent, false))

    override fun getItemCount(): Int = lessonList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(lessonList[position], false)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val favoriteImageView: ImageView
        private val backgroundImageView: ImageView
        private val lessonNameTextView: TextView
        private val downloadStatusTextView: TextView

        private var lessonLocal: LessonLocal? = null

        init {
            itemView.setOnClickListener(this)
            favoriteImageView = itemView.findViewById(R.id.index_rcv_imageview4)
            backgroundImageView = itemView.findViewById(R.id.index_rcv_imageview1)
            lessonNameTextView = itemView.findViewById(R.id.index_rcv_textview2)
            downloadStatusTextView = itemView.findViewById(R.id.index_rcv_textview1)
        }

        fun bind(lessonLocal: LessonLocal, isdownload: Boolean) {
            this.lessonLocal = lessonLocal
            observerDownload(false, lessonLocal)

            lessonNameTextView.text = lessonLocal.name

            favoriteImageView.visibility = View.GONE

            val downloadValue = download.value
            if (downloadValue != null) {
                var publishUrl: String? = null
                for (downloadLocal in downloadValue) {
                    if (downloadLocal.objectId == lessonLocal.objectId) {
                        if (downloadLocal.publishedUrl != null) {
                            publishUrl = downloadLocal.publishedUrl
                        }
                    }
                }
                if (isdownload) {
                    downloadStatusTextView.text = ""
                    when (lessonLocal.subject) {
                        "NURSERY" -> backgroundImageView.setBackgroundResource(R.drawable.nursery)
                        "MUSIC" -> backgroundImageView.setBackgroundResource(R.drawable.music)
                        "READING" -> backgroundImageView.setBackgroundResource(R.drawable.reading)
                        "GAME" -> backgroundImageView.setBackgroundResource(R.drawable.game)
                    }
                } else {
                    if (publishUrl != null) {
                        downloadStatusTextView.text = ""
                        when (lessonLocal.subject) {
                            "NURSERY" -> backgroundImageView.setBackgroundResource(R.drawable.nursery)
                            "MUSIC" -> backgroundImageView.setBackgroundResource(R.drawable.music)
                            "READING" -> backgroundImageView.setBackgroundResource(R.drawable.reading)
                            "GAME" -> backgroundImageView.setBackgroundResource(R.drawable.game)
                        }
                    } else {
                        downloadStatusTextView.text = "点击下载"
                        when (lessonLocal.subject) {
                            "NURSERY" -> backgroundImageView.setBackgroundResource(R.drawable.nursery_gray)
                            "MUSIC" -> backgroundImageView.setBackgroundResource(R.drawable.music_gray)
                            "READING" -> backgroundImageView.setBackgroundResource(R.drawable.reading_gray)
                            "GAME" -> backgroundImageView.setBackgroundResource(R.drawable.game_gray)
                        }
                    }
                }
            }
        }

        private fun observerDownload(editor: Boolean, lesson: LessonLocal) {
            val (isDownloading, myDownloadHolder: com.tipchou.sunshineboxiii.ui.index.DownloadHolder?) = isDownloading(editor, lesson)
            if (isDownloading && myDownloadHolder != null) {
                downloadingQueue.observe(activity, object : Observer<HashMap<DownloadHolder, String>> {
                    override fun onChanged(t: HashMap<DownloadHolder, String>?) {
                        if (t != null) {
                            var isMyHolderInMap = false
                            for (downloadHolder in t.keys) {
                                if (downloadHolder == myDownloadHolder) {
                                    isMyHolderInMap = true
                                    break
                                }
                            }
                            if (isMyHolderInMap) {
                                downloadStatusTextView.text = t[myDownloadHolder]
                            } else {
                                downloadingQueue.removeObserver(this)
                                bind(lesson, true)
                            }
                        } else {
                            //should not be here!!!
                        }
                    }
                })
            }
        }

        private fun isDownloading(editor: Boolean, lesson: LessonLocal): Pair<Boolean, DownloadHolder?> {
            val downloadHolderMap = downloadingQueue.value
            var isDownloading = false
            var myDownloadHolder: DownloadHolder? = null
            if (downloadHolderMap != null) {
                for (downloadHolder in downloadHolderMap.keys) {
                    if (downloadHolder.editor == editor && downloadHolder.lessonObjectId == lesson.objectId) {
                        isDownloading = true
                        myDownloadHolder = downloadHolder
                        break
                    }
                }
            } else {
                //should not be here
            }
            return Pair(isDownloading, myDownloadHolder)
        }

        override fun onClick(v: View?) {
            val lessonLocal = this.lessonLocal
            val downloadValue = download.value
            if (downloadValue != null) {
                var publishUrl: String? = null
                for (downloadLocal in downloadValue) {
                    if (downloadLocal.objectId == lessonLocal?.objectId) {
                        if (downloadLocal.publishedUrl != null) {
                            publishUrl = downloadLocal.publishedUrl
                        }
                    }
                }
                if (publishUrl != null && lessonLocal != null) {
                    activity.startActivity(CourseActivity.newIntent(activity, lessonLocal.objectId, publishUrl))
                } else {
                    toDownload(DownloadHolder(lessonObjectId = lessonLocal?.objectId!!, editor = false, downloadUrl = lessonLocal.packageUrl!!))
                    observerDownload(false, lessonLocal)
                }
            }
        }
    }
}
