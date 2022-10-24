package com.noble.qlit.ui.activity.results

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.noble.qlit.R
import com.noble.qlit.ui.activity.base.InitView
import com.noble.qlit.ui.components.GradesScreen
import com.noble.qlit.ui.components.MaterialTopAppBar
import com.noble.qlit.ui.components.MyScaffold
import com.noble.qlit.ui.theme.fontHead
import com.noble.qlit.utils.ActivityCollector

/**
 * @author: noble
 * @desc: ResultView 登录及成绩查询
 */
@Composable
fun ResultsView() {
    val model: ResultsViewModel = viewModel()
    if (!model.isLogin) {
        InitView {
            MyScaffold(topBar = { MaterialTopAppBar(title = stringResource(id = R.string.results_title)) }) {
                LoginDialog()
            }
        }
    } else {
        model.getResults()
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "grades") {
            composable("grades") { GradesScreen(navController = navController, model = model) }
            composable("rank") {}
        }
    }
}

// 登录 Dialog
@Composable
fun LoginDialog() {
    val model: ResultsViewModel = viewModel()
    val yzm = remember { model.yzm }
    AlertDialog(
        onDismissRequest = { ActivityCollector.finishTopActivity() },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = R.string.results_dialog_title),
                    color = MaterialTheme.colors.secondary,
                    fontSize = 20.sp
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                CaptchaPic()
                OutlinedTextField(
                    value = yzm.value,
                    onValueChange = { model.yzm.value = it },
                    label = { Text(text = stringResource(id = R.string.results_dialog_yzm)) },
                    textStyle = TextStyle(color = MaterialTheme.colors.fontHead),
                    maxLines = 1
                )
            }
        },
        confirmButton = {
            OutlinedButton(
                onClick = { model.attemptLogin() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.results_dialog_button))
            }
        }
    )
}

@Composable
fun CaptchaPic() {
    val model: ResultsViewModel = viewModel()
    if (!model.bitmapFlag) {
        model.getCaptchaPic()
    }
    val pic = remember { model.bitmap }
    Image(
        modifier = Modifier
            .clickable { model.getCaptchaPic() }
            .size(100.dp),
        bitmap = pic.value,
        contentDescription = "验证码图片"
    )
}

