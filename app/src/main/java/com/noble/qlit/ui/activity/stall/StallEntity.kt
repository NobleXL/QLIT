package com.noble.qlit.ui.activity.stall

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @author: NobleXL
 * @desc: StallEntity
 */
@JsonClass(generateAdapter = true)
data class StallEntity(
    @Json(name = "imgUrl")
    val imageUrl: String,
    val title: String,
    val price: String,
    val time: String,
    val phone: Int?,
    val vx: String?,
    val qq: Int?
)

data class StallListResponse(val data: List<StallEntity>?) : BaseResponse()

data class StallInfoResponse(val data: StallEntity?) : BaseResponse()
