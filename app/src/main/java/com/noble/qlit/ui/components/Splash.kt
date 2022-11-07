package com.noble.qlit.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.noble.qlit.R
import com.noble.qlit.ui.activity.base.InitView
import com.noble.qlit.ui.theme.canvas
import com.noble.qlit.utils.DataManager
import com.noble.qlit.utils.getDate
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author: noble
 * @desc: SplashScreen 启动页
 */
@Composable
fun SplashScreen(onSplashCompleted: () -> Unit) {

    InitView {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(16.dp)
            ) {
                LaunchedEffect(Unit) {
                    delay(1000)
                    onSplashCompleted()
                }
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.mipmap.icon_smile),
                        contentDescription = "Logo",
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = getDate(),
                        color = MaterialTheme.colors.canvas,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = "遇见你，真美好",
                        color = MaterialTheme.colors.canvas,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

