package com.tipchou.sunshineboxiii.ui.index.special

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.tipchou.sunshineboxiii.R
import com.tipchou.sunshineboxiii.entity.local.SpecialSubjectLocal
import com.tipchou.sunshineboxiii.ui.index.IndexActivity

/**
 * Created by 邵励治 on 2018/5/29.
 * Perfect Code
 */
class SpecialRecyclerView2(private val activity: IndexActivity, fragment: SpecialFragment) : RecyclerView.Adapter<SpecialRecyclerView2.ViewHolder>() {
    private val layoutInflater: LayoutInflater = LayoutInflater.from(activity)

    private val viewModel: SpecialViewModel = ViewModelProviders.of(activity).get(SpecialViewModel::class.java)

    private val dataset = arrayListOf<SpecialSubjectLocal>()

    init {
        val specialSubject = viewModel.getSpecialSubject()
        specialSubject.observe(fragment, Observer {
            dataset.clear()
            val specialSubjectList = it?.data
            if (specialSubjectList != null) {
                dataset.addAll(specialSubjectList)
            }
            notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(layoutInflater.inflate(R.layout.fragment_special_item2, parent, false))

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(dataset[position])

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val button: Button
        private var specialSubjectLocal: SpecialSubjectLocal? = null

        init {
            itemView.setOnClickListener(this)
            button = itemView.findViewById(R.id.special_rcv2_button1)
        }

        @SuppressLint("SetTextI18n")
        fun bind(specialSubjectLocal: SpecialSubjectLocal) {
            this.specialSubjectLocal = specialSubjectLocal
            button.text = "#" + specialSubjectLocal.title
        }

        override fun onClick(v: View?) {

        }


    }
}
