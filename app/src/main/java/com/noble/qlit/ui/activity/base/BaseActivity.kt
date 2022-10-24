package com.noble.qlit.ui.activity.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.noble.qlit.ui.theme.QlitTheme
import com.noble.qlit.utils.ActivityCollector

/**
 * @author: noble
 * @desc: 统一管理Activity
 */
open class BaseActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}

@Composable
fun InitView(context: @Composable () -> Unit) {
    QlitTheme {
        Surface(color = MaterialTheme.colors.background) {
            context()
        }
    }
}