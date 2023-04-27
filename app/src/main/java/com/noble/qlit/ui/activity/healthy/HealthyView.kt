package com.noble.qlit.ui.activity.healthy

import android.util.Log
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.noble.qlit.R
import com.noble.qlit.data.remote.EasyOkhttp
import com.noble.qlit.ui.activity.base.InitView
import com.noble.qlit.ui.components.GradesScreen
import com.noble.qlit.ui.components.NavigationItem
import com.noble.qlit.ui.theme.fontHead
import com.noble.qlit.utils.ActivityCollector
import com.noble.qlit.utils.DataManager
import com.noble.qlit.utils.getDate
import kotlinx.coroutines.launch

/**
 * @author: NobleXL
 * @desc: HealthyView 健康打卡
 */
@Composable
fun HealthyView() {

    val model: HealthyViewModel = viewModel()
    DataSettings()
    if (!model.isLogin) {
        InitView {
            LoginDialog()
        }
    }else {
        HealthyFrame()
    }
}

// 登录 Dialog
@Composable
fun LoginDialog() {
    val model: HealthyViewModel = viewModel()
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
                    label = { Text(text = stringResource(id = R.string.results_dialog_yzm))},
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
fun HealthyWebView() {
    // 健康截图链接
    val webUrl = "http://pass.qlit.edu.cn/student/student/healthReport/healthReportList.jsp"
    AndroidView(
        factory = { context ->
            val webView = WebView(context)
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            // 把从OkHttp中获取到的Cookie传入到WebView中
            CookieManager.allowFileSchemeCookies()
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.removeSessionCookies{value -> Log.d("TAG", "WebView: $value")}
            cookieManager.setCookie(webUrl, EasyOkhttp.cookie)
            cookieManager.flush()
            // 开启WebView
            webView.apply {
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.domStorageEnabled = true
                settings.loadsImagesAutomatically = true
                settings.mediaPlaybackRequiresUserGesture = false
                webViewClient = WebViewClient()
                loadUrl(webUrl)
            }
        })
}

@Composable
fun ClassScheduleWebView() {
    // 健康截图链接
    val webUrl = "http://pass.qlit.edu.cn/student/student/studentsyllabus/studentsyllabus.jsp"
    AndroidView(
        factory = { context ->
            val webView = WebView(context)
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            // 把从OkHttp中获取到的Cookie传入到WebView中
            CookieManager.allowFileSchemeCookies()
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.removeSessionCookies{value -> Log.d("TAG", "WebView: $value")}
            cookieManager.setCookie(webUrl, EasyOkhttp.cookie)
            cookieManager.flush()
            // 开启WebView
            webView.apply {
                settings.javaScriptEnabled = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.domStorageEnabled = true
                settings.loadsImagesAutomatically = true
                settings.mediaPlaybackRequiresUserGesture = false
                webViewClient = WebViewClient()
                loadUrl(webUrl)
            }
        })
}

@Composable
fun CaptchaPic() {
    val model: HealthyViewModel = viewModel()
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

// 健康按钮变颜色通过时间差异做对比
@Composable
fun DataSettings() {
    LaunchedEffect(key1 = Unit) {
        var data by mutableStateOf("")
        data = getDate()
        launch {
            DataManager.saveData("data", data)
        }
    }
}

// 底部导航栏
@Composable
fun HealthyFrame() {
    val navigationItems = listOf(
        NavigationItem(title = "打卡", icon = Icons.Filled.Home),
        NavigationItem(title = "课程表", icon = Icons.Filled.DateRange)
    )

    var currentNavigationIndex by remember {
        mutableStateOf(0)
    }

    Scaffold(bottomBar = {
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
    }) {
        Box(modifier = Modifier.padding(it)) {
            when (currentNavigationIndex) {
                0 -> HealthyWebView()
                1 -> ClassScheduleWebView()
            }
        }
    }
}