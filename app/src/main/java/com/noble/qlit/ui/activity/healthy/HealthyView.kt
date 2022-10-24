package com.noble.qlit.ui.activity.healthy

import android.util.Log
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.noble.qlit.R
import com.noble.qlit.data.remote.EasyOkhttp
import com.noble.qlit.data.repository.RemoteRepository
import com.noble.qlit.ui.activity.base.InitView
import com.noble.qlit.ui.components.GradesScreen
import com.noble.qlit.ui.components.MaterialTopAppBar
import com.noble.qlit.ui.components.MyScaffold
import com.noble.qlit.ui.theme.fontHead
import com.noble.qlit.utils.ActivityCollector

/**
 * @author: NobleXL
 * @desc: HealthyView 健康打卡
 */
@Composable
fun HealthyView() {

    val model: HealthyViewModel = viewModel()
    if (!model.isLogin) {
        InitView {
            LoginDialog()
        }
    }else {
        WebView()
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
fun WebView() {
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

