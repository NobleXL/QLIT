package com.noble.qlit.ui.activity.stall

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.noble.qlit.data.repository.RemoteRepository
import com.noble.qlit.ui.activity.home.HomeView
import com.noble.qlit.ui.activity.results.ResultsActivity
import com.noble.qlit.ui.components.NavigationItem
import com.noble.qlit.utils.LogUtil
import com.noble.qlit.utils.start

/**
 * @author: NobleXL
 * @desc: StallFrame
 */

// 底部导航栏
@Composable
fun StallFrame(
    stalloneViewModel: StallOneViewModel,
    jobViewModel: JobViewModel,
    onNavigateToStallone: () -> Unit = {},
    onNavigateToJob: () -> Unit = {}
) {

    val navigationItems = listOf(
        NavigationItem(title = "交易", icon = Icons.Filled.Home),
        NavigationItem(title = "校园", icon = Icons.Filled.DateRange)
    )

    var currentNavigationIndex by remember {
        mutableStateOf(0)
    }

    Scaffold(bottomBar = {
        Box {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.background,
                modifier = Modifier.navigationBarsPadding()
            ) {
                navigationItems.forEachIndexed { index, navigationItem ->
                    BottomNavigationItem(
                        selected = currentNavigationIndex == index,
                        onClick = {
                            currentNavigationIndex = index
                        },
                        icon = {
                            Icon(
                                imageVector = navigationItem.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = navigationItem.title)
                        },
                        alwaysShowLabel = false
                    )
                }
            }
            IconButton(
                onClick = { start<UploadActivity>() },
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(shape = CircleShape)
                    .background(Color(0x22149EE7))
                    .clickable(
                        onClick = { },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )

            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add"
                )
            }
        }
    }) {
        Box(modifier = Modifier.padding(it)) {
            when (currentNavigationIndex) {
                0 -> StallActivity(
                    stalloneViewModel = stalloneViewModel,
                    jobViewModel = jobViewModel,
                    onNavigateToStallone = onNavigateToStallone,
                    onNavigateToJob = onNavigateToJob
                )

                1 -> HomeView()
            }
        }
    }

}
