package com.noble.qlit.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable

/**
 * @author: noble
 * @desc: 脚手架
 */
@Composable
fun MyScaffold(
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        content = {
            Column {
                content()
            }
        },
        bottomBar = bottomBar
    )
}

