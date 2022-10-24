package com.noble.qlit.ui.activity.healthy

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noble.qlit.data.repository.RemoteRepository
import com.noble.qlit.utils.ActivityCollector
import com.noble.qlit.utils.start
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

/**
 * @author: NobleXL
 * @desc: HealthyViewModel
 */
class HealthyViewModel : ViewModel() {

    var bitmapFlag: Boolean = false
    var isLogin: Boolean = false
    var bitmap = mutableStateOf(ImageBitmap(200, 200))
    var yzm = mutableStateOf("")

    fun attemptLogin() {
        viewModelScope.launch {
            if (RemoteRepository.HealthyLogin(yzm.value)) {
                gotoResults()
            } else {
                getCaptchaPic()
                yzm.value = ""
            }
        }
    }

    private fun gotoResults() {
        ActivityCollector.finishTopActivity()
        start<HealthyActivity>() {
            putExtra("isLogin", true)
        }
    }

    // 获取验证码
    fun getCaptchaPic() {
        thread {
            RemoteRepository.getCaptchaPic()?.let {
                bitmap.value = it.asImageBitmap()
            }
        }
        bitmapFlag = true
    }
}