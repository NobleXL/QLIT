package com.noble.qlit.ui.activity.stall


import android.util.Log
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
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
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import kotlin.math.log

/**
 * @author: NobleXL
 * @desc: StallItem
 */
@Composable
fun StallItem(stallone: StallEntity, loaded: Boolean, modifier: Modifier = Modifier) {

    val constraintSet = ConstraintSet {
        val title = createRefFor("title")
        val cover = createRefFor("cover")
        val price = createRefFor("price")
        val duration = createRefFor("duration")
        val divider = createRefFor("divider")

        constrain(cover) {
            start.linkTo(parent.start)
            centerVerticallyTo(parent)
            width = Dimension.value(255.dp)
        }

        constrain(title) {
            top.linkTo(parent.top, margin = 8.dp)
            start.linkTo(cover.end, margin = 8.dp)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }

        constrain(price) {
            start.linkTo(title.start)
            bottom.linkTo(duration.top, margin = 8.dp)
        }

        constrain(duration) {
            start.linkTo(price.start)
            bottom.linkTo(divider.top, margin = 16.dp)
        }

        constrain(divider) {
            bottom.linkTo(parent.bottom, margin = (-8).dp)
        }
    }

    ConstraintLayout(
        constraintSet,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            model = stallone.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .layoutId("cover")
                .aspectRatio(16 / 9f)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .placeholder(
                    visible = !loaded,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = Color.LightGray
                )
        )

        Text(
            text = stallone.title,
            fontSize = 16.sp,
            color = Color(0xFF666666),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .layoutId("title")
                .placeholder(
                    visible = !loaded,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = Color.LightGray
                )
        )

        Text(
            text = stallone.price,
            fontSize = 16.sp,
            color = Color(0xFFFF4757),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .layoutId("price")
                .placeholder(
                    visible = !loaded,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = Color.LightGray
                )
        )

        Text(
            text = "时间：${stallone.time}",
            fontSize = 12.sp,
            color = Color(0xFF999999),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .layoutId("duration")
                .placeholder(
                    visible = !loaded,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = Color.LightGray
                )
        )

        Divider(modifier = Modifier.layoutId("divider"))
    }
}

