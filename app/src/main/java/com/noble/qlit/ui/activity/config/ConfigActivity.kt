package com.noble.qlit.ui.activity.config

import android.os.Bundle
import androidx.activity.compose.setContent
import com.noble.qlit.ui.activity.base.BaseActivity

/**
 * @author: noble
 * @desc: ConfigActivity
 */
class ConfigActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConfigView()
        }
    }
}