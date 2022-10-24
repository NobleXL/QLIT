package com.noble.qlit.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.noble.qlit.R
import com.noble.qlit.data.bean.Course
import com.noble.qlit.ui.activity.base.InitView
import com.noble.qlit.ui.activity.results.ResultsViewModel
import com.noble.qlit.ui.theme.Teal200
import kotlinx.coroutines.launch

/**
 * @author: noble
 * @desc: Navigation 成绩导航集合
 */
@Composable
fun GradesScreen(navController: NavController, model: ResultsViewModel) {
    val load = remember { model.isLoaded }
    InitView {
        MyScaffold(
            topBar = {
                MaterialTopAppBar(title = stringResource(id = R.string.results_title))
            },
            bottomBar = {
                BottomNav(navController)
            }
        ) {
            if (load.value) GradesPager(model)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GradesPager(model: ResultsViewModel) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val tabs = model.termCode
    val terms = model.termGrade
    ScrollableTabRow(
        // 我们选择的标签是我们当前的页面
        selectedTabIndex = pagerState.currentPage,
        // 使用提供的 pagerTabIndicatorOffset 修饰符覆盖指标
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        backgroundColor = MaterialTheme.colors.background
    ) { // 为我们所有的页面添加标签
        tabs.forEachIndexed { index, title ->
            Tab(
                text = { Text(text = title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    // 单击时动画到所选页面
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
    HorizontalPager(
        count = tabs.size,
        state = pagerState
    ) {
        val courses = terms[pagerState.currentPage]
        println("--> ${pagerState.currentPage}  ${courses.size}")
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, top = 10.dp, bottom = 80.dp)
        ) {
            courses.forEach {
                item { CourseItem(it) }
            }
        }
    }
}

@Composable
fun CourseItem(course: Course) {
    Column(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = course.courseName,
                fontSize = 18.sp,
                modifier = Modifier.width(300.dp)
            )
            Text(
                text = course.courseScore,
                textAlign = TextAlign.End,
                fontSize = 18.sp,
                color = Teal200
            )
        }
    }
}

