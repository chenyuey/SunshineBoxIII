package com.tipchou.sunshineboxiii.entity.local

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/**
 * Created by 邵励治 on 2018/5/2.
 * Perfect Code
 */
@Entity
class RoleLocal(@Id var id: Long = 0, val name: String?)
