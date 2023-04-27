package com.noble.qlit.ui.activity.stall

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import kotlinx.coroutines.launch
import java.util.*

/**
 * @author 小寒
 * @version 1.0
 * @date 2022/7/1 16:46
 */

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SwiperContent(vm: StallViewModel) {

    //虚拟页数
    val virtualCount = Int.MAX_VALUE

    //实际页数
    val actualCount = vm.swiperData.size

    //初始图片下标
    val initialIndex = virtualCount / 2

    val pagerState = rememberPagerState(initialPage = initialIndex)

    val coroutineScope = rememberCoroutineScope()

    //实现自动轮播
    DisposableEffect(Unit) {

        coroutineScope.launch {
            vm.swiperData()
        }

        val timer = Timer()

        timer.schedule(object : TimerTask() {
            override fun run() {
                if (vm.swiperLoaded) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }

        }, 3000, 3000)

        onDispose {
            timer.cancel()
        }
    }

    HorizontalPager(
        count = virtualCount,
        state = pagerState,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp)), //给图片设置圆角
        userScrollEnabled = vm.swiperLoaded
    ) { index ->
        val actualIndex =
            (index - initialIndex).floorMod(actualCount) //index - (index.floorDiv(actualCount)) * actualCount
        AsyncImage(
            model = vm.swiperData[actualIndex].imageUrl,
            contentDescription = null,
            //给图片设置大小
            modifier = Modifier
                .fillMaxWidth()
                //设置比例
                .aspectRatio(7 / 3f)
                .placeholder(
                    visible = !vm.swiperLoaded,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = Color.LightGray
                ),
            //设置裁剪
            contentScale = ContentScale.Crop
        )
    }

}

fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

