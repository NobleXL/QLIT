package com.noble.qlit.ui.activity.healthy

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.noble.qlit.ui.activity.base.BaseActivity
import com.noble.qlit.utils.ActivityCollector

/**
 * @author: NobleXL
 * @desc: HealthyActivity 界面
 */
class HealthyActivity: BaseActivity() {

    lateinit var viewModel: HealthyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(HealthyViewModel::class.java)
        viewModel.isLogin = intent.getBooleanExtra("isLogin", false)
        setContent {
            HealthyView()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCollector.finishTopActivity()
    }
}