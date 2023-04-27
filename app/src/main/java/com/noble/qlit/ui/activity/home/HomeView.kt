package com.noble.qlit.ui.activity.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Extension
import androidx.compose.material.icons.rounded.ManageSearch
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.noble.qlit.R
import com.noble.qlit.data.repository.RemoteRepository
import com.noble.qlit.ui.activity.base.InitView
import com.noble.qlit.ui.activity.config.ConfigActivity
import com.noble.qlit.ui.activity.healthy.HealthyActivity
import com.noble.qlit.ui.activity.results.ResultsActivity
import com.noble.qlit.ui.components.*
import com.noble.qlit.ui.theme.Teal200
import com.noble.qlit.utils.DataManager
import com.noble.qlit.utils.getDate
import com.noble.qlit.utils.start
import kotlinx.coroutines.launch

/**
 * @author: NobleXL
 * @desc: HomeView
 */
@Composable
fun HomeView() {
    InitView {
        MyScaffold(
            topBar = { HomeAppBar() },
            content = {
                CardCheckIn()
                CardGradeQuery()
                ListCardConfig()
            }
        )
    }
}

// 核酸打卡
@Composable
fun CardCheckIn() {
    var data by remember {
        mutableStateOf("")
    }
    var isTodayCheckIn by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = Unit) {
        launch {
            data = DataManager.readData("data", "")
            isTodayCheckIn = data == getDate()
        }
    }

    CardItem(
        isLarge = true,
        isActive = isTodayCheckIn,
        onClick = {
            RemoteRepository.pic = true
            start<HealthyActivity>()
            isTodayCheckIn = true
        },
        title = stringResource(id = R.string.home_card_checkin),
        body = if (isTodayCheckIn) {
            stringResource(id = R.string.home_checkin_true)
        } else {
            stringResource(id = R.string.home_checkin_false)
        }
    )
}

// 成绩单
@Composable
fun CardGradeQuery() {
    CardItem(
        title = stringResource(id = R.string.home_card_results),
        body = "｡^‿^｡",
        icon = Icons.Rounded.ManageSearch,
        onClick = {
            RemoteRepository.pic = false
            start<ResultsActivity>()
        }
    )
}

// 配置
@Composable
fun ListCardConfig() {
    ListCardItem(
        title = stringResource(id = R.string.home_list_config),
        icon = Icons.Outlined.Extension,
        onClick = {
            start<ConfigActivity>()
        }
    )
}

// 顶部栏
@Composable
fun HomeAppBar() {
    val hitoko by RemoteRepository.getHitokoto()
        .collectAsState(initial = stringResource(id = R.string.home_subtitle))
    MaterialAppBar(
        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
        title = {
            Column {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = hitoko,
                    color = Teal200,
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis // 超出就用省略号
                )
            }
        },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.home_logo),
                contentDescription = "Home logo"
            )
        },
        actions = {

        }
    )
}

