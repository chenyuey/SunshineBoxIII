package com.tipchou.sunshineboxiii.support

import android.os.AsyncTask
import android.util.Log

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created by 邵励治 on 2018/3/2.
 * Perfect Code
 */

class DownloadTask constructor(private val responseBodyResponse: Response<ResponseBody>,
                               outputFileName: String,
                               outputFolder: File,
                               private val downloadCallback: DownloadCallback)
    : AsyncTask<Void, Long, Void>() {

    private var isFileDownloadSuccess: Boolean? = null

    private val downloadFileAddress: File = File(outputFolder, outputFileName)

    interface DownloadCallback {
        fun downloadSuccess(file: File)
        fun downloadFailure(file: File)
        fun progressUpdate(value: Long?)
    }

    override fun doInBackground(vararg voids: Void): Void? {
        isFileDownloadSuccess = writeResponseBodyToDisk(responseBodyResponse.body()!!, downloadFileAddress)
        Log.e("IndexAdapter", "file download was a success?$isFileDownloadSuccess")
        return null
    }

    override fun onPostExecute(aVoid: Void) {
        when (isFileDownloadSuccess) {
            true -> downloadCallback.downloadSuccess(downloadFileAddress)
            false -> downloadCallback.downloadFailure(downloadFileAddress)
        }
        super.onPostExecute(aVoid)
    }

    override fun onProgressUpdate(vararg values: Long?) {
        super.onProgressUpdate(*values)
        val progress = values[0]
        if (progress != null) {
            downloadCallback.progressUpdate(progress)
        }
    }

    private fun writeResponseBodyToDisk(body: ResponseBody, file: File): Boolean {
        try {
            // todo change the file location/name according to your needs
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body.byteStream()
                outputStream = FileOutputStream(file)

                while (true) {
                    val read = inputStream!!.read(fileReader)

                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)

                    fileSizeDownloaded += read.toLong()

                    val downloadPercent = fileSizeDownloaded.toFloat() / fileSize.toFloat()
                    val result = (downloadPercent * 100).toLong()
                    publishProgress(result)
                }

                outputStream.flush()

                return true
            } catch (e: IOException) {
                return false
            } finally {
                if (inputStream != null) {
                    inputStream.close()
                }

                if (outputStream != null) {
                    outputStream.close()
                }
            }
        } catch (e: IOException) {
            return false
        }

    }
}
