package com.tipchou.sunshineboxiii.entity.web

import com.avos.avoscloud.AVRole

/**
 * Created by 邵励治 on 2018/5/2.
 * Perfect Code
 */
class RoleWeb constructor(role: AVRole) {
    val name: String? = role.name
    override fun toString(): String {
        return "RoleWeb(name=$name)"
    }
}
