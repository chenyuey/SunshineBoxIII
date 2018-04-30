package com.tipchou.sunshineboxiii.entity.web

import com.avos.avoscloud.AVObject

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */
class TestWeb constructor(avObject: AVObject?) {
    val user_id: String? = avObject?.getString("user_id")
    val user_name: String? = avObject?.getString("user_name")
}