package com.noble.qlit.ui.activity.stall

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateBefore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import kotlinx.coroutines.delay
import kotlin.math.log

/**
 * @author: NobleXL
 * @desc: StallJobScreen
 */
@Composable
fun StallScreen(stalloneViewModel: StallOneViewModel, onBack: () -> Unit) {

    LaunchedEffect(Unit) {
        stalloneViewModel.fetchInfo()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.Transparent,
                contentColor = MaterialTheme.colors.onSurface,
                elevation = 0.dp, // 不需要阴影
                title = {
                    Text(
                        text = "商品详情",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.h6
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.NavigateBefore,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                onBack()
                            }
                            .padding(8.dp)
                    )
                }
            )
        }
    ) {
        val constraintSet = ConstraintSet {
            val title = createRefFor("title")
            val cover = createRefFor("cover")
            val price = createRefFor("price")
            val qq = createRefFor("qq")
            val vx = createRefFor("vx")
            val phone = createRefFor("phone")

            constrain(cover) {
                start.linkTo(parent.start)
                width = Dimension.fillToConstraints
            }

            constrain(title) {
                top.linkTo(cover.bottom, margin = 8.dp)
                start.linkTo(cover.start, margin = 8.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }

            constrain(price) {
                top.linkTo(title.bottom, margin = 8.dp)
                start.linkTo(cover.start, margin = 8.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }

            constrain(phone) {
                top.linkTo(price.bottom, margin = 8.dp)
                start.linkTo(cover.start, margin = 8.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }

            constrain(vx) {
                top.linkTo(phone.bottom, margin = 8.dp)
                start.linkTo(cover.start, margin = 8.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }

            constrain(qq) {
                top.linkTo(vx.bottom, margin = 8.dp)
                start.linkTo(cover.start, margin = 8.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        }

        ConstraintLayout(
            constraintSet,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = stalloneViewModel.stallEntity.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .layoutId("cover")
                    .aspectRatio(1 / 1f)
                    .clip(RoundedCornerShape(8.dp))
                    .placeholder(
                        visible = !stalloneViewModel.listsLoaded,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = Color.LightGray
                    )
            )

            Text(
                text = stalloneViewModel.stallEntity.title,
                fontSize = 22.sp,
                color = Color(0xFF666666),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .layoutId("title")
                    .placeholder(
                        visible = !stalloneViewModel.listsLoaded,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = Color.LightGray
                    )
            )

            Text(
                text = stalloneViewModel.stallEntity.price,
                fontSize = 20.sp,
                color = Color(0xFFFF4757),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .layoutId("price")
                    .placeholder(
                        visible = !stalloneViewModel.listsLoaded,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = Color.LightGray
                    )
            )

            Text(
                text = if (stalloneViewModel.stallEntity.phone != null) "Phone: " + stalloneViewModel.stallEntity.phone else "",
                fontSize = 18.sp,
                color = Color(0xFF666666),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .layoutId("phone")
                    .placeholder(
                        visible = !stalloneViewModel.listsLoaded,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = Color.LightGray
                    )
            )

            Text(
                text = if (stalloneViewModel.stallEntity.vx != null) "VX: " + stalloneViewModel.stallEntity.vx else "",
                fontSize = 18.sp,
                color = Color(0xFF666666),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .layoutId("vx")
                    .placeholder(
                        visible = !stalloneViewModel.listsLoaded,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = Color.LightGray
                    )
            )

            Text(
                text = if (stalloneViewModel.stallEntity.qq != null) "QQ: " + stalloneViewModel.stallEntity.qq else "",
                fontSize = 18.sp,
                color = Color(0xFF666666),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .layoutId("qq")
                    .placeholder(
                        visible = !stalloneViewModel.listsLoaded,
                        highlight = PlaceholderHighlight.shimmer(),
                        color = Color.LightGray
                    )
            )
        }
    }

}

