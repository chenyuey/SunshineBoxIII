package com.tipchou.sunshineboxiii.entity.local

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by 邵励治 on 2018/5/2.
 * Perfect Code
 */
@Entity
class LessonLocal(@Id var id: Long = 0,
                  val isPublish: Boolean?,
                  val tags: String?,
                  val packageUrl: String?,
                  val name: String?,
                  val versionCode: Int?,
                  val draftVersionCode: Int?,
                  val subject: String?,
                  val compiler: String?,
                  val stagingPackageUrl: String?,
                  val areChecked: Int?)
