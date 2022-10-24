package com.noble.qlit.ui.activity.config

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.rounded.ManageSearch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.noble.qlit.R
import com.noble.qlit.ui.activity.base.InitView
import com.noble.qlit.ui.components.ExpandableCard
import com.noble.qlit.ui.components.MaterialTopAppBar
import com.noble.qlit.ui.components.MyScaffold
import com.noble.qlit.ui.theme.fontBody
import com.noble.qlit.ui.theme.fontHead
import com.noble.qlit.utils.LogUtil

/**
 * @author: noble
 * @desc: ConfigView
 */
@Composable
fun ConfigView() {
    val model: ConfigViewModel = viewModel()
    model.showConfig()
    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
        InitView {
            MyScaffold(topBar = { ConfigAppBar() }) {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    item { GradeItem() }
                    item { HealthyItem() }
                }
            }
        }
    }
}

// 帐号密码
@Composable
fun GradeItem() {
    ExpandableCard(
        title = stringResource(id = R.string.home_card_results),
        icon = Icons.Rounded.ManageSearch,
        // content 当 expandedState = true 调用
        content = {
            val model: ConfigViewModel = viewModel()
            val accountState = remember { model.account }
            val passwordState = remember { model.password }
            Column {
                MyTextField(
                    state = accountState,
                    label = stringResource(id = R.string.config_account)
                )
                MyTextField(
                    state = passwordState,
                    label = stringResource(id = R.string.config_password),
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        }
    )
}

// 健康打卡
@Composable
fun HealthyItem() {
    ExpandableCard(
        title = stringResource(id = R.string.home_card_checkin),
        icon = Icons.Outlined.CheckCircleOutline,
        content = {
            val model: ConfigViewModel = viewModel()
            val idcardState = remember { model.IDcard }
            val idendState = remember { model.ID_end }
            val dqjkzkState = remember { model.dqjkzk }
            val dqszsState = remember { model.dqszs }
            val dqszcState = remember { model.dqszc }
            val dqszqState = remember { model.dqszq }
            val sfyjzymState = remember { model.sfyjzym }
            val sfjzzyState = remember { model.sfjzzy }
            val sfjcysState = remember { model.sfjcys }
            val swtwState = remember { model.swtw }
            val zwtwState = remember { model.zwtw }
            val xwtwState = remember { model.xwtw }
            Column {
                MyTextField(
                    state = idcardState, 
                    label = stringResource(id = R.string.config_idcard) 
                )
                MyTextField(
                    state = idendState,
                    label = stringResource(id = R.string.config_idend),
                    visualTransformation = PasswordVisualTransformation()
                )
                MyTextField(
                    state = dqjkzkState,
                    label = stringResource(id = R.string.config_dqjkzk)
                )
                MyTextField(
                    state = dqszsState,
                    label = stringResource(id = R.string.config_dqszs)
                )
                MyTextField(
                    state = dqszcState,
                    label = stringResource(id = R.string.config_dqszc)
                )
                MyTextField(
                    state = dqszqState,
                    label = stringResource(id = R.string.config_dqszq)
                )
                MyTextField(
                    state = sfyjzymState,
                    label = stringResource(id = R.string.config_sfyjzym)
                )
                MyTextField(
                    state = sfjzzyState,
                    label = stringResource(id = R.string.config_sfjzzy)
                )
                MyTextField(
                    state = sfjcysState,
                    label = stringResource(id = R.string.config_sfjcys)
                )
                MyTextField(
                    state = swtwState,
                    label = stringResource(id = R.string.config_swtw)
                )
                MyTextField(
                    state = zwtwState,
                    label = stringResource(id = R.string.config_zwtw)
                )
                MyTextField(
                    state = xwtwState,
                    label = stringResource(id = R.string.config_xwtw)
                )
            }
        }
    )
}

// 通用输入框
@Composable
fun MyTextField(
    state: MutableState<String>,
    label: String,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = state.value,
            readOnly = readOnly,
            visualTransformation = visualTransformation,
            onValueChange = { state.value = it },
            label = { Text(label) },
            textStyle = TextStyle(color = MaterialTheme.colors.fontHead),
            trailingIcon = @Composable {
                Image(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable { state.value = "" })
            },
            modifier = Modifier.width(300.dp)
        )
    }
}

// 顶部标题栏
@Composable
fun ConfigAppBar() {
    val model: ConfigViewModel = viewModel()
    MaterialTopAppBar(
        title = stringResource(id = R.string.home_list_config),
        actions = {
            IconButton(onClick = {
                model.saveConfig()
                LogUtil.d("ConfigAppBar", "保存配置")
            }) {
                Icon(imageVector = Icons.Outlined.Save, contentDescription = "保存配置")
            }
        }
    )
}


