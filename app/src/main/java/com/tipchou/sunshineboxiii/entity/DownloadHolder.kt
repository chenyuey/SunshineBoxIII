package com.tipchou.sunshineboxiii.entity

/**
 * Created by 邵励治 on 2018/5/14.
 * Perfect Code
 */
class DownloadHolder(val lessonObjectId: String, val editor: Boolean, val downloadUrl: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DownloadHolder

        if (lessonObjectId != other.lessonObjectId) return false
        if (editor != other.editor) return false
        if (downloadUrl != other.downloadUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = lessonObjectId.hashCode()
        result = 31 * result + editor.hashCode()
        result = 31 * result + downloadUrl.hashCode()
        return result
    }
}
