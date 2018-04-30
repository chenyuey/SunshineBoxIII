package com.tipchou.sunshineboxiii.entity.local

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */
@Entity
class UsersLocal(@Id var id: Long = 0, val userId: String, val userName: String)