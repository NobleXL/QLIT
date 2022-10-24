package com.noble.qlit.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.noble.qlit.R

/**
 * @author: noble
 * @desc: 成绩底部Buttom
 */
@Composable
fun BottomNav(navController: NavController) {
    val items = listOf(
        stringResource(id = R.string.results_bottom_grades),
//        stringResource(id = R.string.results_bottom_rank)
    )

    BottomNavigation(
        modifier = Modifier.padding(16.dp),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 3.dp
    ) {
        items.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = {
                    if (index == 1) {
                        Icon(imageVector = Icons.Outlined.TrendingUp, contentDescription = null)
                    } else {
                        Icon(imageVector = Icons.Outlined.Assessment, contentDescription = null)
                    }
                },
                selected = false,
                onClick = {
                    if (index == 1) {
                        navController.navigate("rank")
                    } else {
                        navController.navigate("grades")
                    }
                }
            )
        }
    }
}

