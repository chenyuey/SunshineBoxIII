package com.tipchou.sunshineboxiii.ui.index.lesson

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.support.LessonType
import com.tipchou.sunshineboxiii.ui.base.BaseFragment
import com.tipchou.sunshineboxiii.ui.index.IndexRecyclerViewAdapter
import com.tipchou.sunshineboxiii.ui.index.IndexViewModel
import kotlinx.android.synthetic.main.fragment_lesson.*

/**
 * Created by 邵励治 on 2018/5/29.
 * Perfect Code
 */
class LessonFragment : BaseFragment() {
    private var viewModel: IndexViewModel? = null
    private fun setUpClickEvent() {
        //subject button
        lesson_fgm_linearlayout1.setOnClickListener {
            viewModel?.setLessonType(LessonType.NURSERY)
            viewModel?.loadLesson()
        }
        lesson_fgm_linearlayout2.setOnClickListener {
            viewModel?.setLessonType(LessonType.MUSIC)
            viewModel?.loadLesson()
        }
        lesson_fgm_linearlayout3.setOnClickListener {
            viewModel?.setLessonType(LessonType.READING)
            viewModel?.loadLesson()
        }
        lesson_fgm_linearlayout4.setOnClickListener {
            viewModel?.setLessonType(LessonType.GAME)
            viewModel?.loadLesson()
        }
        //tag button
        lesson_fgm_linearlayout5.setOnClickListener {
            viewModel?.setLessonType(LessonType.HEALTH)
            viewModel?.loadLesson()
        }
        lesson_fgm_linearlayout6.setOnClickListener {
            viewModel?.setLessonType(LessonType.LANGUAGE)
            viewModel?.loadLesson()
        }
        lesson_fgm_linearlayout7.setOnClickListener {
            viewModel?.setLessonType(LessonType.SOCIAL)
            viewModel?.loadLesson()
        }
        lesson_fgm_linearlayout8.setOnClickListener {
            viewModel?.setLessonType(LessonType.SCIENCE)
            viewModel?.loadLesson()
        }
        lesson_fgm_linearlayout9.setOnClickListener {
            viewModel?.setLessonType(LessonType.ART)
            viewModel?.loadLesson()
        }
    }

    override fun layoutId(): Int = R.layout.fragment_lesson

    private fun makeEveryBodyGray() {
        if (lesson_fgm_textview1.currentTextColor == Color.parseColor("#f09038")) {
            lesson_fgm_textview1.setTextColor(Color.parseColor("#666666"))
            lesson_fgm_imageview1.background = mActivity?.getDrawable(R.drawable.ic_nursery_black)
        }

        if (lesson_fgm_textview2.currentTextColor == Color.parseColor("#f09038")) {
            lesson_fgm_textview2.setTextColor(Color.parseColor("#666666"))
            lesson_fgm_imageview2.background = mActivity?.getDrawable(R.drawable.ic_music_black)
        }

        if (lesson_fgm_textview3.currentTextColor == Color.parseColor("#f09038")) {
            lesson_fgm_textview3.setTextColor(Color.parseColor("#666666"))
            lesson_fgm_imageview3.background = mActivity?.getDrawable(R.drawable.ic_reading_black)
        }

        if (lesson_fgm_textview4.currentTextColor == Color.parseColor("#f09038")) {
            lesson_fgm_textview4.setTextColor(Color.parseColor("#666666"))
            lesson_fgm_imageview4.background = mActivity?.getDrawable(R.drawable.ic_game_black)
        }

        if (lesson_fgm_textview5.currentTextColor == Color.parseColor("#f09038")) {
            lesson_fgm_textview5.setTextColor(Color.parseColor("#666666"))
            lesson_fgm_imageview5.background = mActivity?.getDrawable(R.drawable.ic_health_black)
        }

        if (lesson_fgm_textview6.currentTextColor == Color.parseColor("#f09038")) {
            lesson_fgm_textview6.setTextColor(Color.parseColor("#666666"))
            lesson_fgm_imageview6.background = mActivity?.getDrawable(R.drawable.ic_language_black)
        }

        if (lesson_fgm_textview7.currentTextColor == Color.parseColor("#f09038")) {
            lesson_fgm_textview7.setTextColor(Color.parseColor("#666666"))
            lesson_fgm_imageview7.background = mActivity?.getDrawable(R.drawable.ic_social_black)
        }

        if (lesson_fgm_textview8.currentTextColor == Color.parseColor("#f09038")) {
            lesson_fgm_textview8.setTextColor(Color.parseColor("#666666"))
            lesson_fgm_imageview8.background = mActivity?.getDrawable(R.drawable.ic_science_black)
        }

        if (lesson_fgm_textview9.currentTextColor == Color.parseColor("#f09038")) {
            lesson_fgm_textview9.setTextColor(Color.parseColor("#666666"))
            lesson_fgm_imageview9.background = mActivity?.getDrawable(R.drawable.ic_art_black)
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(IndexViewModel::class.java)
        viewModel?.getNetStatus()?.observe(this, Observer {

        })
        viewModel?.getLessonType()?.observe(this, Observer {
            when (it) {
                LessonType.NURSERY -> {
                    makeEveryBodyGray()
                    lesson_fgm_textview1.setTextColor(Color.parseColor("#f09038"))
                    lesson_fgm_imageview1.background = mActivity?.getDrawable(R.drawable.ic_nursery_orange)
                }
                LessonType.MUSIC -> {
                    makeEveryBodyGray()
                    lesson_fgm_textview2.setTextColor(Color.parseColor("#f09038"))
                    lesson_fgm_imageview2.background = mActivity?.getDrawable(R.drawable.ic_music_orange)

                }
                LessonType.READING -> {
                    makeEveryBodyGray()
                    lesson_fgm_textview3.setTextColor(Color.parseColor("#f09038"))
                    lesson_fgm_imageview3.background = mActivity?.getDrawable(R.drawable.ic_reading_orange)

                }
                LessonType.GAME -> {
                    makeEveryBodyGray()
                    lesson_fgm_textview4.setTextColor(Color.parseColor("#f09038"))
                    lesson_fgm_imageview4.background = mActivity?.getDrawable(R.drawable.ic_game_orange)

                }
                LessonType.HEALTH -> {
                    makeEveryBodyGray()
                    lesson_fgm_textview5.setTextColor(Color.parseColor("#f09038"))
                    lesson_fgm_imageview5.background = mActivity?.getDrawable(R.drawable.ic_health_orange)
                }
                LessonType.LANGUAGE -> {
                    makeEveryBodyGray()
                    lesson_fgm_textview6.setTextColor(Color.parseColor("#f09038"))
                    lesson_fgm_imageview6.background = mActivity?.getDrawable(R.drawable.ic_language_orange)
                }
                LessonType.SOCIAL -> {
                    makeEveryBodyGray()
                    lesson_fgm_textview7.setTextColor(Color.parseColor("#f09038"))
                    lesson_fgm_imageview7.background = mActivity?.getDrawable(R.drawable.ic_social_orange)

                }
                LessonType.SCIENCE -> {
                    makeEveryBodyGray()
                    lesson_fgm_textview8.setTextColor(Color.parseColor("#f09038"))
                    lesson_fgm_imageview8.background = mActivity?.getDrawable(R.drawable.ic_science_orange)
                }
                LessonType.ART -> {
                    makeEveryBodyGray()
                    lesson_fgm_textview9.setTextColor(Color.parseColor("#f09038"))
                    lesson_fgm_imageview9.background = mActivity?.getDrawable(R.drawable.ic_art_orange)

                }
            }
        })
        viewModel?.getRole()?.observe(this, Observer {
            Log.e("Role", "Status: ${it?.status.toString()}; Message: ${it?.message}; Size: ${it?.data?.size}")
        })
        viewModel?.getLesson()?.observe(this, Observer {
            Log.e("Lesson", "Status: ${it?.status.toString()}; Message: ${it?.message}; Size: ${it?.data?.size}")
        })
    }

    private fun setUpRecyclerView() {
        val adapter = IndexRecyclerViewAdapter(mActivity as AppCompatActivity, viewModel!!)
        lesson_fgm_recyclerview.layoutManager = GridLayoutManager(mActivity, 4)
        lesson_fgm_recyclerview.adapter = adapter
    }

    override fun created(bundle: Bundle?) {
        setUpClickEvent()
        setUpViewModel()
        setUpRecyclerView()
    }


    override fun resumed() {

    }
}
