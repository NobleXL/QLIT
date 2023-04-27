package com.noble.qlit.ui.activity.stall

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author: NobleXL
 * @desc: SwipeEntity
 */
@JsonClass(generateAdapter = true)
data class SwiperEntity(
    @Json(name = "imgUrl")
    val imageUrl: String
)
data class SwiperResponse(val data: List<SwiperEntity>?) : BaseResponse()
