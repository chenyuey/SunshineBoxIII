package com.tipchou.sunshineboxiii.entity.web

import com.avos.avoscloud.AVObject

/**
 * Created by 邵励治 on 2018/5/30.
 * Perfect Code
 */
class LessonSubjectWeb constructor(avObject: AVObject) {
    val objectId: String = avObject.objectId
    val specialObjectId: String
    val lessonObjectId: String

    init {
        val special = avObject.getAVObject<AVObject>("special")
        val lesson = avObject.getAVObject<AVObject>("lesson")
        specialObjectId = special.objectId
        lessonObjectId = lesson.objectId
    }
}