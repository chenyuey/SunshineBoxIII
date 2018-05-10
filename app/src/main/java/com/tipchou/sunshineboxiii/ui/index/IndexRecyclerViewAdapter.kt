package com.tipchou.sunshineboxiii.ui.index

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.entity.local.LessonLocal
import com.tipchou.sunshineboxiii.entity.local.RoleLocal
import com.tipchou.sunshineboxiii.support.Resource

/**
 * Created by 邵励治 on 2018/5/8.
 * Perfect Code
 */
class IndexRecyclerViewAdapter(activity: IndexActivity) : RecyclerView.Adapter<IndexRecyclerViewAdapter.ViewHolder>() {
    class Lesson(val LessonLocal: LessonLocal, val editor: Boolean)

    private val netStatusLiveData: LiveData<Boolean>
    private val roleLiveData: LiveData<Resource<List<RoleLocal>>>
    private val lessonLiveData: LiveData<Resource<List<LessonLocal>>>

    private val layoutInflater: LayoutInflater = LayoutInflater.from(activity)

    private val lesson = ArrayList<Lesson>()

    init {
        val viewModel: IndexViewModel = ViewModelProviders.of(activity).get(IndexViewModel::class.java)
        netStatusLiveData = viewModel.getNetStatus()
        netStatusLiveData.observe(activity, Observer { })
        roleLiveData = viewModel.getRole()
        roleLiveData.observe(activity, Observer { })
        lessonLiveData = viewModel.getLesson()
        lessonLiveData.observe(activity, Observer {
            lesson.clear()
            notifyDataSetChanged()
            buildLessonList(it)
            notifyDataSetChanged()
        })
    }

    private fun buildLessonList(it: Resource<List<LessonLocal>>?) {
        if (it?.status == Resource.Status.SUCCESS || it?.status == Resource.Status.ERROR || it?.status == Resource.Status.LOADING) {
            val roleList: List<RoleLocal>? = roleLiveData.value?.data
            val lessonList = it.data
            if (roleList == null) {
                //should not be here
            } else {
                val isEditor = isUserEditor(roleList)
                if (isEditor) {
                    if (lessonList == null) {
                        //should not be here
                    } else {
                        for (item in lessonList) {
                            if (item.isPublish == true) {
                                lesson.add(Lesson(item, false))
                            }
                        }
                        for (item in lessonList) {
                            if (item.areChecked == 1) {
                                lesson.add(Lesson(item, true))
                            }
                        }
                    }
                } else {
                    if (lessonList == null) {
                        //should not be here
                    } else {
                        for (item in lessonList) {
                            if (item.isPublish == true) {
                                lesson.add(Lesson(item, false))
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

    override fun getItemCount(): Int = lesson.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lesson = lesson[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val backgroundImageView: ImageView
        private val isAuditedImageView: ImageView
        private val downloadStatusTextView: TextView
        private val lessonNameTextView: TextView

        fun bind(lesson: Lesson?) {
            lessonNameTextView.text = lesson?.LessonLocal?.name
            when (lesson?.LessonLocal?.subject) {
                "NURSERY" -> backgroundImageView.setBackgroundResource(R.drawable.nursery)
                "MUSIC" -> backgroundImageView.setBackgroundResource(R.drawable.music)
                "READING" -> backgroundImageView.setBackgroundResource(R.drawable.reading)
                "GAME" -> backgroundImageView.setBackgroundResource(R.drawable.game)
            }
            when (lesson?.editor) {
                false -> isAuditedImageView.visibility = View.GONE
                true -> isAuditedImageView.visibility = View.VISIBLE
            }
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
