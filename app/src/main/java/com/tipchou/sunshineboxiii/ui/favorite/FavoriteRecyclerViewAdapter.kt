package com.tipchou.sunshineboxiii.ui.favorite

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

/**
 * Created by 邵励治 on 2018/5/26.
 * Perfect Code
 */
class FavoriteRecyclerViewAdapter(private val activity: FavoriteActivity) : RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder>() {
    private val viewModel: FavoriteViewModel = ViewModelProviders.of(activity).get(FavoriteViewModel::class.java)
    private val favoriteLessonList = arrayListOf<LessonLocal>()
    private val download: LiveData<List<DownloadLocal>>


    init {
        viewModel.getFavoriteLesson().observe(activity, Observer {
            val data = it?.data
            if (data != null) {
                favoriteLessonList.clear()
                favoriteLessonList.addAll(data)
                notifyDataSetChanged()
            }
        })
        viewModel.getFavorite().observe(activity, Observer {
            viewModel.loadFavoriteLesson()
        })
        download = viewModel.getDownload()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_index_recyclerview, parent, false))

    override fun getItemCount(): Int = favoriteLessonList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(favoriteLessonList[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val favoriteImageView: ImageView
        private val backgroundImageView: ImageView
        private val lessonNameTextView: TextView

        private var lessonLocal: LessonLocal? = null

        init {
            itemView.setOnClickListener(this)
            favoriteImageView = itemView.findViewById(R.id.index_rcv_imageview4)
            backgroundImageView = itemView.findViewById(R.id.index_rcv_imageview1)
            lessonNameTextView = itemView.findViewById(R.id.index_rcv_textview2)
        }

        fun bind(lessonLocal: LessonLocal) {
            this.lessonLocal = lessonLocal
            lessonNameTextView.text = lessonLocal.name
            when (lessonLocal.subject) {
                "NURSERY" -> backgroundImageView.setBackgroundResource(R.drawable.nursery)
                "MUSIC" -> backgroundImageView.setBackgroundResource(R.drawable.music)
                "READING" -> backgroundImageView.setBackgroundResource(R.drawable.reading)
                "GAME" -> backgroundImageView.setBackgroundResource(R.drawable.game)
            }
            favoriteImageView.visibility = View.VISIBLE
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
                    activity.showSnackBar("该课程目前没有下载，请下载后再尝试打开")
                }
            }
        }
    }


}
