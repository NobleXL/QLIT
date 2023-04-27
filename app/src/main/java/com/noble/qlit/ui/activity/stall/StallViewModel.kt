package com.noble.qlit.ui.activity.stall

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * @author: NobleXL
 * @desc: StallViewModel
 */
class StallViewModel : ViewModel() {

    private val homeService = StallService.instance()

    // 类型数据
    val types by mutableStateOf(listOf("二手商品", "校园兼职"))

    // 当前类型下标
    var currentTypeIndex by mutableStateOf(0)
        private set

    //是否显示商品列表
    var showStallList by mutableStateOf(true)
        private set

    // 更新类型下标
    fun updateTypeIndex(index: Int) {
        currentTypeIndex = index
        showStallList = currentTypeIndex == 0
    }

    // 轮播图数据
    var swiperData by mutableStateOf(
        listOf(
            SwiperEntity("https://cdn.staticaly.com/gh/NobleXL/Image-Hosting@main/NobleXL/NobleXL1656937355177.3kdbqo3k9va0.jpg")
        )
    )

    var swiperLoaded by mutableStateOf(false)
        private set

    suspend fun swiperData() {
        val swiperRes = homeService.swipeList()
        if (swiperRes.code == 200 && swiperRes.data != null) {
            swiperData = swiperRes.data
            swiperLoaded = true
        } else {
            val message = swiperRes.message
        }
    }

    //通知数据
    val notifications =
        listOf("特价特价！！！", "ヾ(≧▽≦*)o", "( •̀ ω •́ )✧")
}