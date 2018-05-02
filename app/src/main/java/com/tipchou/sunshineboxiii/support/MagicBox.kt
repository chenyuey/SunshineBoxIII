package com.tipchou.sunshineboxiii.support

import com.tipchou.sunshineboxiii.ui.test.TestRepository
import com.tipchou.sunshineboxiii.ui.test.TestViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by 邵励治 on 2018/4/26.
 * Perfect Code
 */
@Singleton
@Component
interface MagicBox {
    fun poke(testViewModel: TestViewModel)
    fun poke(testRepository: TestRepository)
}