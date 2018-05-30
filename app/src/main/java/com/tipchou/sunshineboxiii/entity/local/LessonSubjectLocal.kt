package com.tipchou.sunshineboxiii.entity.local

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by 邵励治 on 2018/5/30.
 * Perfect Code
 */
@Entity
class LessonSubjectLocal(@Id var id: Long = 0,
                         val objectId: String,
                         val specialObjectId: String,
                         val lessonObjectId: String)
