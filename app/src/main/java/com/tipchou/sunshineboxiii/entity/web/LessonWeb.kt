package com.tipchou.sunshineboxiii.entity.web

import com.avos.avoscloud.AVObject

/**
 * Created by 邵励治 on 2018/5/2.
 * Perfect Code
 */
class LessonWeb constructor(avObject: AVObject) {
    val isPublish: Boolean? = avObject.getBoolean("isPublished")
    val tags: String? = avObject.getList("tags").toString()
}
