package com.tipchou.sunshineboxiii.entity.local

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by 邵励治 on 2018/5/30.
 * Perfect Code
 */
@Entity
class SpecialSubjectLocal(@Id var id: Long = 0,
                          val objectId: String,
                          val recommendStatus: Boolean,
                          val title: String,
                          val pictureUrl: String?,
                          val describe: String,
                          val onLine: Boolean)