package com.tipchou.sunshineboxiii.support

import android.arch.lifecycle.Observer
import io.objectbox.android.ObjectBoxLiveData

/**
 * Created by 邵励治 on 2018/5/3.
 * Perfect Code
 */
class GeneralSaveCallResult<Database, Network> constructor(
        netData: List<Network>,
        requestDb: () -> ObjectBoxLiveData<Database>,
        deleteDb: (List<Database>) -> Unit,
        buildSavedList: (List<Network>) -> List<Database>,
        addDb: (List<Database>) -> Unit
) {
    init {
        val dbData = requestDb()
        dbData.observeForever(object : Observer<List<Database>> {
            override fun onChanged(t: List<Database>?) {
                dbData.removeObserver(this)
                if (t != null) {
                    //remove all data by this request in db
                    deleteDb(t)
                    //put all data by this request in db
                    addDb(buildSavedList(netData))
                }
            }
        })
    }
}
