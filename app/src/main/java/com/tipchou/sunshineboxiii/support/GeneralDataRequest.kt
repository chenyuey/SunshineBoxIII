package com.tipchou.sunshineboxiii.support

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.util.Log

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */
class GeneralDataRequest<ResultType, RequestType> @MainThread
constructor(
        loadFromDb: () -> LiveData<ResultType>,
        shouldFetch: (data: ResultType?) -> Boolean,
        createCall: () -> LiveData<ApiResponse<RequestType>>,
        saveCallResult: (item: RequestType?) -> Unit
) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        val dbSource: LiveData<ResultType> = loadFromDb()
        result.addSource(dbSource) { dbData ->
            result.removeSource(dbSource)
            if (shouldFetch(dbData)) {
                result.addSource(dbSource) {
                    result.value = Resource.loading(it)
                }
                val netSource: LiveData<ApiResponse<RequestType>> = createCall()
                result.addSource(netSource) { netData ->
                    result.removeSource(netSource)
                    result.removeSource(dbSource)
                    if (netData != null) {
                        if (netData.isSuccessful()) {
                            saveCallResult(netData.getBody())
                            result.addSource(loadFromDb()) {
                                result.value = Resource.success(it)
                            }
                        } else {
                            result.addSource(dbSource) {
                                result.value = Resource.error(netData.getErrorMessage(), it)
                            }
                        }
                    } else {
                        //should not be here!!!
                        Log.e("GeneralDataRequest", "netData is null!!!")
                    }
                }
            } else {
                result.addSource(dbSource) {
                    result.value = Resource.success(it)
                }
            }
        }
    }

    fun getAsLiveData(): LiveData<Resource<ResultType>> {
        return result
    }
}
