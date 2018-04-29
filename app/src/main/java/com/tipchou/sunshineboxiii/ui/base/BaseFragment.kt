package com.tipchou.sunshineboxiii.ui.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by 邵励治 on 2018/3/26.
 * Perfect Code
 */

abstract class BaseFragment : Fragment() {
    protected var mActivity: Activity? = null
    protected var mFragment: View? = null

    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun created(bundle: Bundle?)

    protected abstract fun resumed()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mFragment = inflater.inflate(layoutId(), container, false)
        return mFragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        created(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        resumed()
    }



    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mActivity = context as Activity?
    }
}