package com.noble.qlit.ui.activity.stall

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.noble.qlit.R
import com.noble.qlit.utils.LogUtil
import kotlinx.coroutines.delay

/**
 * @author: NobleXL
 * @desc: StallOneViewModel
 */
class StallOneViewModel : ViewModel() {

    private val stallService = StallService.instance()

    // 二手商品数据
    var list by mutableStateOf(
        listOf(
            StallEntity(
                imageUrl = "https://cdn.staticaly.com/gh/NobleXL/Image-Hosting@main/NobleXL/NobleXLR.cxy4wjxw29k.webp",
                title = "二手小汽车",
                price = "${10000.0} 元",
                time = "2023-04-20 12:17",
                phone = null,
                vx = "noble",
                qq = null
            ),
            StallEntity(
                imageUrl = "https://cdn.staticaly.com/gh/NobleXL/Image-Hosting@main/NobleXL/NobleXLR.cxy4wjxw29k.webp",
                title = "二手小汽车",
                price = "${10000.0} 元",
                time = "2023-04-20 12:17",
                phone = null,
                vx = "noble",
                qq = null
            ),
            StallEntity(
                imageUrl = "https://cdn.staticaly.com/gh/NobleXL/Image-Hosting@main/NobleXL/NobleXLR.cxy4wjxw29k.webp",
                title = "二手小汽车",
                price = "${10000.0} 元",
                time = "2023-04-20 12:17",
                phone = 10010,
                vx = "noble",
                qq = null
            ),
            StallEntity(
                imageUrl = "https://cdn.staticaly.com/gh/NobleXL/Image-Hosting@main/NobleXL/NobleXLR.cxy4wjxw29k.webp",
                title = "二手小汽车",
                price = "${10000.0} 元",
                time = "2023-04-20 12:17",
                phone = null,
                vx = "noble",
                qq = null
            ),
            StallEntity(
                imageUrl = "https://cdn.staticaly.com/gh/NobleXL/Image-Hosting@main/NobleXL/NobleXLR.cxy4wjxw29k.webp",
                title = "二手小汽车",
                price = "${10000.0} 元",
                time = "2023-04-20 12:17",
                phone = null,
                vx = "noble",
                qq = null
            )
        )
    )
        private set

    // 商品数据是否加载完成
    var listLoaded by mutableStateOf(false)
        private set

    suspend fun fetchStallList() {
        val res = stallService.stallList()
        if (res.code == 200 && res.data != null) {
            list = res.data
            listLoaded = true
            refreshing = false
        }
    }

    // 是否正在刷新
    var refreshing by mutableStateOf(false)
        private set

    suspend fun refresh() {
        refreshing = true
        fetchStallList()
    }

    var stallEntity by mutableStateOf(
        StallEntity(
            imageUrl = "https://cdn.staticaly.com/gh/NobleXL/Image-Hosting@main/NobleXL/NobleXLR.cxy4wjxw29k.webp",
            title = "二手小汽车",
            price = "${10000.0} 元",
            time = "2023-04-20 12:17",
            phone = null,
            vx = "noble",
            qq = null
        )
    )

    // 商品信息数据是否加载完成
    var listsLoaded by mutableStateOf(false)
        private set

    var id by mutableStateOf("")

    suspend fun fetchInfo() {
        val res = stallService.info(id)
        if (res.code == 200 && res.data != null) {
            stallEntity = res.data
            listsLoaded = true
        }
    }

}