package com.tipchou.sunshineboxiii.entity.web

import com.avos.avoscloud.AVObject

/**
 * Created by 邵励治 on 2018/5/2.
 * Perfect Code
 */
class FavoriteWeb constructor(avObject: AVObject) {
    val action: Boolean = avObject.getBoolean("action")
    val lessonId: String = avObject.getString("lessonId")
}
