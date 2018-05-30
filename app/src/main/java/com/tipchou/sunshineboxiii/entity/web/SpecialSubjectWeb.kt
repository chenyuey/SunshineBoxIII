package com.tipchou.sunshineboxiii.entity.web

import com.avos.avoscloud.AVFile
import com.avos.avoscloud.AVObject

/**
 * Created by 邵励治 on 2018/5/30.
 * Perfect Code
 */
class SpecialSubjectWeb constructor(avObject: AVObject) {
    val objectId: String = avObject.objectId
    val recommendStatus: Boolean = avObject.getBoolean("recommendStatus")
    val title: String = avObject.getString("title")
    val pictureUrl: String? = avObject.getAVFile<AVFile>("picture")?.url
    val describe: String = avObject.getString("describe")
    val onLine: Boolean = avObject.getBoolean("onLine")
}
