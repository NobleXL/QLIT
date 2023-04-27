package com.noble.qlit.ui.activity.stall

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

/**
 * @author: NobleXL
 * @desc: StallActivity
 */
@Composable
fun StallActivity(
    vm: StallViewModel = viewModel(),
    stalloneViewModel: StallOneViewModel,
    jobViewModel: JobViewModel,
    onNavigateToStallone: () -> Unit = {},
    onNavigateToJob: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
        // 获取商品列表数据
        stalloneViewModel.fetchStallList()
        // 获取兼职列表数据
        jobViewModel.fetchStallList()
    }

    val lazyListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier) {
        TabRow(
            selectedTabIndex = vm.currentTypeIndex,
            backgroundColor = Color.Transparent,
            contentColor = Color(0xFF149EE7),
            indicator = {},
            divider = {}
        ) {
            vm.types.forEachIndexed { index, item ->
                Tab(selected = vm.currentTypeIndex == index, onClick = {
                    vm.updateTypeIndex(index)
                }) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(vertical = 8.dp),
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = stalloneViewModel.refreshing),
            onRefresh = {
                coroutineScope.launch {
                    stalloneViewModel.refresh()
                }
            }) {
            LazyColumn(state = lazyListState) {

                // 轮播图
                item {
                    SwiperContent(vm = vm)
                }

                // 通知
                item {
                    NotificationContent(vm = vm)
                }

                if (vm.showStallList) {
                    // 商品列表
                    itemsIndexed(stalloneViewModel.list) { index, stallone ->
                        StallItem(
                            stallone,
                            stalloneViewModel.listLoaded,
                            modifier = Modifier
                                .clickable {
                                    stalloneViewModel.id = (index + 1).toString()
                                    onNavigateToStallone()
                                }
                        )
                    }
                } else {
                    // 兼职列表
                    itemsIndexed(jobViewModel.list) { index, job ->
                        JobItem(
                            job,
                            jobViewModel.listLoaded,
                            modifier = Modifier
                                .clickable {
                                    jobViewModel.id = (index + 1).toString()
                                    onNavigateToJob()
                                }
                        )
                    }
                }

            }

        }
    }
}


