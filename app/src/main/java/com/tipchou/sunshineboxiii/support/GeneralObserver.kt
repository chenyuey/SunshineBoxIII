package com.tipchou.sunshineboxiii.support

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer

/**
 * Created by 邵励治 on 2018/4/30.
 * Perfect Code
 */
class GeneralObserver<T> constructor(val data: MutableLiveData<T>, private val request: () -> LiveData<T>) : Observer<T> {
    override fun onChanged(t: T?) {
        data.value = t
    }

    private var observedData: LiveData<T>? = null

    fun load() {
        if (observedData != null) {
            observedData?.removeObserver(this)
            observedData = null
        }
        observedData = request()
        observedData?.observeForever(this)
    }

}
