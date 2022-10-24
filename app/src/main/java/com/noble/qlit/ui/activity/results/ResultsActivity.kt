package com.noble.qlit.ui.activity.results

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.noble.qlit.ui.activity.base.BaseActivity
import com.noble.qlit.utils.ActivityCollector

/**
 * @author: noble
 * @desc: ResultsActivity
 */
class ResultsActivity : BaseActivity() {

    lateinit var viewModel: ResultsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ResultsViewModel::class.java)
        viewModel.isLogin = intent.getBooleanExtra("isLogin", false)
        setContent {
            ResultsView()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCollector.finishTopActivity()
    }
}