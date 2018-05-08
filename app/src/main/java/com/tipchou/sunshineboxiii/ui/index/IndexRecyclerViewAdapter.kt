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

    private val netStatus: LiveData<Boolean>
    private val role: LiveData<Resource<List<RoleLocal>>>
    private val lesson: LiveData<Resource<List<LessonLocal>>>

    private val layoutInflater: LayoutInflater = LayoutInflater.from(activity)

    init {
        val viewModel: IndexViewModel = ViewModelProviders.of(activity).get(IndexViewModel::class.java)
        netStatus = viewModel.getNetStatus()
        netStatus.observe(activity, Observer { })
        role = viewModel.getRole()
        role.observe(activity, Observer { })
        lesson = viewModel.getLesson()
        lesson.observe(activity, Observer {
            notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(layoutInflater.inflate(R.layout.item_index_recyclerview, parent, false))

    override fun getItemCount(): Int = lesson.value?.data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lesson = lesson.value?.data?.get(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val backgroundImageView: ImageView
        private val isAuditedImageView: ImageView
        private val downloadStatusTextView: TextView
        private val lessonNameTextView: TextView

        fun bind(lesson: LessonLocal?) {
            lessonNameTextView.text = lesson?.name
            when (lesson?.subject) {
                "NURSERY" -> backgroundImageView.setBackgroundResource(R.drawable.nursery)
                "MUSIC" -> backgroundImageView.setBackgroundResource(R.drawable.music)
                "READING" -> backgroundImageView.setBackgroundResource(R.drawable.reading)
                "GAME" -> backgroundImageView.setBackgroundResource(R.drawable.game)
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
