package com.tipchou.sunshineboxiii.support

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */
class GeneralDataRequest<Database, Network> @MainThread
constructor(
        loadFromDb: () -> LiveData<List<Database>>,
        shouldFetch: (data: List<Database>) -> Boolean,
        createCall: () -> LiveData<ApiResponse<List<Network>>>,
        saveCallResult: (item: List<Network>) -> Unit
) {
    private val result = MediatorLiveData<Resource<List<Database>>>()

    init {
        result.value = Resource.loading(null)
        val dbSource: LiveData<List<Database>> = loadFromDb()
        result.addSource(dbSource) { dbData ->
            result.removeSource(dbSource)
            if (dbData == null) {
                //should not be here
            } else {
                if (shouldFetch(dbData)) {
                    result.addSource(dbSource) {
                        result.value = Resource.loading(it)
                    }
                    val netSource: LiveData<ApiResponse<List<Network>>> = createCall()
                    result.addSource(netSource) { netData ->
                        result.removeSource(netSource)
                        result.removeSource(dbSource)
                        if (netData == null) {
                            //should not be here!!!
                        } else {
                            if (netData.isSuccessful()) {
                                val body = netData.getBody()
                                if (body == null) {
                                    //should not be here!!!
                                } else {
                                    saveCallResult(body)
                                    result.addSource(loadFromDb()) {
                                        result.value = Resource.success(it)
                                    }
                                }
                            } else {
                                result.addSource(dbSource) {
                                    result.value = Resource.error(netData.getErrorMessage(), it)
                                }
                            }
                        }
                    }
                } else {
                    result.addSource(dbSource) {
                        result.value = Resource.success(it)
                    }
                }
            }

        }
    }

    fun getAsLiveData(): LiveData<Resource<List<Database>>> {
        return result
    }
}
